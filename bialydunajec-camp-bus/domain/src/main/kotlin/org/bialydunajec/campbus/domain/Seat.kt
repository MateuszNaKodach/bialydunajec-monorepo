package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.AggregateRoot
import org.bialydunajec.eventsourcing.domain.DomainCommand
import org.bialydunajec.eventsourcing.domain.EventSourcedAggregateRoot
import org.bialydunajec.eventsourcing.domain.TimeProvider

sealed class Seat(
        currentTimeProvider: TimeProvider,
        aggregateId: SeatId,
        changes: List<SeatEvent>)
    : EventSourcedAggregateRoot<SeatId, SeatCommand, SeatEvent, Seat>(currentTimeProvider, aggregateId, changes, SeatEvent::class) {


    companion object {
        fun recreateFrom(currentTimeProvider: TimeProvider, domainEvents: List<SeatEvent>) =
                domainEvents.fold(newInstance(currentTimeProvider)) { acc: Seat, domainEvent: SeatEvent -> acc.replayEvent(domainEvent) }

        fun newInstance(currentTimeProvider: TimeProvider): Seat = Uninitialized(currentTimeProvider, SeatId.undefined(), emptyList())
    }


    private class Uninitialized(currentTimeProvider: TimeProvider, aggregateId: SeatId, events: List<SeatEvent>) : Seat(currentTimeProvider, aggregateId, events) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.AddSeatForCourse -> SeatEvent.SeatAddedForCourse(
                            command.aggregateId,
                            currentTimeProvider(),
                            command.campBusCourseId
                    )
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatAddedForCourse -> Free(
                            currentTimeProvider,
                            lastEvent.aggregateId,
                            lastEvent.campBusCourseId,
                            uncommittedHistory
                    )
                    else -> this
                }

    }

    /*
            TODO: Rozważyć czy nie zmienić takiego kodu na komendach:

         internal fun on(seat: Seat.Free, timestamp: Instant) = SeatEvent.SeatReservedForPassenger(aggregateId, seat.aggregateVersion, timestamp, seat.campBusCourseId, passengerId)

         a takiego na aggregatach:

         override fun process(command: SeatCommand): SeatEvent =
        command.on(this, timestamp)

        wtedy mamy zachowane Open-Closed Principle, ale trochę odpowiedzialności się dziwnie rozkłdają - komenda wie kiedy może zostać wykonana
        Chyba to nie zadziąła, jeśli case biznesowy potrzebuje np. serwisu domenowego w aggregacie.
 */

    private class Free(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, events: List<SeatEvent>) : Seat(currentTimeProvider, aggregateId, events) {

        override fun process(command: SeatCommand): SeatEvent =
                when (command) {
                    is SeatCommand.ReserveSeat -> SeatEvent.SeatReservedForPassenger(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            command.passengerId
                    )
                    is SeatCommand.RemoveSeatFromCourse -> SeatEvent.SeatRemovedFromCourse(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            null
                    )
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReservedForPassenger -> Reserved(
                            currentTimeProvider,
                            lastEvent.aggregateId,
                            campBusCourseId,
                            lastEvent.passengerId,
                            uncommittedHistory
                    )
                    is SeatEvent.SeatRemovedFromCourse -> Removed(
                            currentTimeProvider,
                            lastEvent.aggregateId,
                            campBusCourseId,
                            uncommittedHistory
                    )
                    else -> this
                }

        override fun toString() = "Free(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId)"


    }


    private class Reserved(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>) : Seat(currentTimeProvider, aggregateId, events) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.ConfirmReservation -> SeatEvent.SeatReservationConfirmed(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            passengerId
                    )
                    is SeatCommand.CancelReservation -> SeatEvent.SeatReservationCancelled(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            passengerId
                    )
                    is SeatCommand.RemoveSeatFromCourse -> SeatEvent.SeatRemovedFromCourse(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            passengerId
                    )
                    is SeatCommand.ReserveSeat -> SeatEvent.SeatReservationFailed(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            command.passengerId,
                            SeatDomainRule.SeatForReservationCannotBeAlreadyReserved
                    )
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReservationCancelled -> Free(
                            currentTimeProvider,
                            lastEvent.aggregateId,
                            campBusCourseId,
                            uncommittedHistory
                    )
                    is SeatEvent.SeatReservationConfirmed -> Occupied(
                            currentTimeProvider,
                            lastEvent.aggregateId,
                            campBusCourseId,
                            lastEvent.passengerId,
                            uncommittedHistory
                    )
                    is SeatEvent.SeatRemovedFromCourse -> Removed(
                            currentTimeProvider,
                            lastEvent.aggregateId,
                            campBusCourseId,
                            uncommittedHistory
                    )
                    else -> Reserved(
                            currentTimeProvider,
                            aggregateId,
                            campBusCourseId,
                            passengerId,
                            uncommittedHistory
                    )
                }


        override fun toString() = "Reserved(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId, passengerId=$passengerId)"

    }

    private class Occupied(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>) : Seat(currentTimeProvider, aggregateId, events) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.ReleaseSeat -> SeatEvent.SeatReleased(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            passengerId
                    )
                    is SeatCommand.RemoveSeatFromCourse -> SeatEvent.SeatRemovedFromCourse(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            passengerId
                    )
                    is SeatCommand.ReserveSeat -> SeatEvent.SeatReservationFailed(
                            command.aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            command.passengerId,
                            SeatDomainRule.SeatForReservationCannotBeOccupiedByAnotherPassenger
                    )
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReleased -> Free(
                            currentTimeProvider,
                            lastEvent.aggregateId,
                            campBusCourseId,
                            uncommittedHistory
                    )
                    is SeatEvent.SeatRemovedFromCourse -> Removed(
                            currentTimeProvider,
                            lastEvent.aggregateId,
                            campBusCourseId,
                            uncommittedHistory
                    )
                    else -> Occupied(
                            currentTimeProvider,
                            aggregateId,
                            campBusCourseId,
                            passengerId,
                            uncommittedHistory
                    )
                }

        override fun toString() = "Occupied(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId, passengerId=$passengerId)"

    }

    private class Removed(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, events: List<SeatEvent>) : Seat(currentTimeProvider, aggregateId, events) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.ReserveSeat -> SeatEvent.SeatReservationFailed(
                            aggregateId,
                            currentTimeProvider(),
                            campBusCourseId,
                            command.passengerId,
                            SeatDomainRule.SeatForReservationCannotBeRemoved
                    )
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent): Seat =
                Removed(
                        currentTimeProvider,
                        aggregateId,
                        campBusCourseId,
                        uncommittedHistory
                )

        override fun toString() = "Removed(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId)"


    }


}


class UnprocessableCommandException(val command: DomainCommand<*>, val aggregate: AggregateRoot<*, *>)
    : IllegalStateException("Command: <$command> cannot be processed by aggregate root: <$aggregate>!")

