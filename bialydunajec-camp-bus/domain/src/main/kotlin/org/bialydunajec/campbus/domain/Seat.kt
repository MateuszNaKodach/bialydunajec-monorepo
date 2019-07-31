package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.*

sealed class Seat(
        currentTimeProvider: TimeProvider,
        aggregateId: SeatId,
        changes: List<SeatEvent>,
        aggregateVersion: AggregateVersion)
    : EventSourcedAggregateRoot<SeatId, SeatCommand, SeatEvent, Seat>(currentTimeProvider, aggregateId, aggregateVersion, changes, SeatEvent::class) {

    override fun toString() = "Seat(aggregateVersion=$aggregateVersion)"

    companion object {
        fun recreateFrom(currentTimeProvider: TimeProvider, domainEvents: List<SeatEvent>) =
                domainEvents.fold(newInstance(currentTimeProvider)) { acc: Seat, domainEvent: SeatEvent -> acc.replayEvent(domainEvent) }

        fun newInstance(currentTimeProvider: TimeProvider): Seat = Uninitialized(currentTimeProvider, SeatId.undefined(), emptyList(), AggregateVersion.ZERO)
    }


    private class Uninitialized(currentTimeProvider: TimeProvider, aggregateId: SeatId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.AddSeatForCourse -> SeatEvent.SeatAddedForCourse(command.aggregateId, aggregateVersion, currentTimeProvider(), command.campBusCourseId)
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextAggregateVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatAddedForCourse -> Free(currentTimeProvider, lastEvent.aggregateId, lastEvent.campBusCourseId, uncommittedHistory, nextAggregateVersion)
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

    private class Free(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

        override fun process(command: SeatCommand): SeatEvent =
                when (command) {
                    is SeatCommand.ReserveSeat -> SeatEvent.SeatReservedForPassenger(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, command.passengerId)
                    is SeatCommand.RemoveSeatFromCourse -> SeatEvent.SeatRemovedFromCourse(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, null)
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextAggregateVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReservedForPassenger -> Reserved(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, lastEvent.passengerId, uncommittedHistory, nextAggregateVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommittedHistory, nextAggregateVersion)
                    else -> this
                }

        override fun toString() = "Free(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId, aggregateVersion=$aggregateVersion)"


    }


    private class Reserved(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.ConfirmReservation -> SeatEvent.SeatReservationConfirmed(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, passengerId)
                    is SeatCommand.CancelReservation -> SeatEvent.SeatReservationCancelled(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, passengerId)
                    is SeatCommand.RemoveSeatFromCourse -> SeatEvent.SeatRemovedFromCourse(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, passengerId)
                    is SeatCommand.ReserveSeat -> command.failureEvent {
                        //TODO: pass command as it!
                        SeatEvent.SeatReservationFailed(
                                command.aggregateId,
                                aggregateVersion,
                                currentTimeProvider(),
                                campBusCourseId,
                                command.passengerId,
                                SeatDomainRule.SeatForReservationCannotBeAlreadyReserved
                        )
                    }
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextAggregateVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReservationCancelled -> Free(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommittedHistory, nextAggregateVersion)
                    is SeatEvent.SeatReservationConfirmed -> Occupied(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, lastEvent.passengerId, uncommittedHistory, nextAggregateVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommittedHistory, nextAggregateVersion)
                    else -> this
                }


        override fun toString() = "Free(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId, passengerId=$passengerId, aggregateVersion=$aggregateVersion)"

    }

    private class Occupied(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.ReleaseSeat -> SeatEvent.SeatReleased(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, passengerId)
                    is SeatCommand.RemoveSeatFromCourse -> SeatEvent.SeatRemovedFromCourse(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, passengerId)
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextAggregateVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReleased -> Free(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommittedHistory, nextAggregateVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommittedHistory, nextAggregateVersion)
                    else -> this
                }

        override fun toString() = "Free(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId, passengerId=$passengerId, aggregateVersion=$aggregateVersion)"

    }

    private class Removed(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

        override fun process(command: SeatCommand) =
                throw UnprocessableCommandException(command, this)

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextAggregateVersion: AggregateVersion): Seat =
                this

        override fun toString() = "Free(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId, aggregateVersion=$aggregateVersion)"


    }


}


class UnprocessableCommandException(val command: DomainCommand<*>, val aggregate: AggregateRoot<*, *>)
    : IllegalStateException("Command: <$command> cannot be processed by aggregate root: <$aggregate>!")

