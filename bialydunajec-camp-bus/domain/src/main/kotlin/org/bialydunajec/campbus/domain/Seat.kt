package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.*


sealed class Seat(protected val currentTimeProvider: TimeProvider, override val aggregateId: SeatId, override val changes: List<SeatEvent>, override val aggregateVersion: AggregateVersion)
    : EventSourcedAggregateRoot<SeatId, SeatEvent> {

    fun replayEvent(event: SeatEvent) = applyEvent(event, EventApplyingMode.REPLAY_HISTORY)

    fun applyEvent(event: SeatEvent, mode: EventApplyingMode = EventApplyingMode.DEFAULT): Seat =
            composeOf(
                    when (mode) {
                        EventApplyingMode.APPLY_NEW_CHANGE -> listOf<SeatEvent>(*changes.toTypedArray(), event)
                        EventApplyingMode.REPLAY_HISTORY -> changes
                    },
                    event,
                    aggregateVersion.increase()
            )

    fun handle(command: SeatCommand): Seat {
        if (this.aggregateVersion !== command.aggregateVersion) {
            throw AggregateVersionMismatchException(aggregateVersion, command.aggregateVersion)
        }
        return applyEvent(process(command))
    }

    abstract fun process(command: SeatCommand): SeatEvent

    protected abstract fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextAggregateVersion: AggregateVersion): Seat

    override fun toString() = "Seat(aggregateVersion=$aggregateVersion)"

    companion object {
        fun recreateFrom(currentTimeProvider: TimeProvider, domainEvents: List<SeatEvent>) =
                domainEvents.fold(newInstance(currentTimeProvider)) { acc: Seat, domainEvent: SeatEvent -> acc.replayEvent(domainEvent) }

        fun newInstance(currentTimeProvider: TimeProvider): Seat = Uninitialized(currentTimeProvider, SeatId.undefined(), emptyList(), AggregateVersion.ZERO)
    }

    internal class Uninitialized(currentTimeProvider: TimeProvider, aggregateId: SeatId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

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

    internal class Free(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

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


    internal class Reserved(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.ConfirmReservation -> SeatEvent.SeatReservationConfirmed(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, passengerId)
                    is SeatCommand.CancelReservation -> SeatEvent.SeatReservationCancelled(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, passengerId)
                    is SeatCommand.RemoveSeatFromCourse -> SeatEvent.SeatRemovedFromCourse(command.aggregateId, aggregateVersion, currentTimeProvider(), campBusCourseId, passengerId)
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

    internal class Occupied(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

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

    internal class Removed(currentTimeProvider: TimeProvider, aggregateId: SeatId, val campBusCourseId: BusCourseId, events: List<SeatEvent>, aggregateVersion: AggregateVersion) : Seat(currentTimeProvider, aggregateId, events, aggregateVersion) {

        override fun process(command: SeatCommand) =
                throw UnprocessableCommandException(command, this)

        override fun composeOf(uncommittedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextAggregateVersion: AggregateVersion): Seat =
                this

        override fun toString() = "Free(aggregateId=$aggregateId, campBusCourseId=$campBusCourseId, aggregateVersion=$aggregateVersion)"


    }


}


class UnprocessableCommandException(val command: Command<*>, val aggregate: AggregateRoot<*, *>)
    : IllegalStateException("Command: <$command> cannot be processed by aggregate root: <$aggregate>!")

interface StateMachineAggregate
interface EventSourcedAggregate

