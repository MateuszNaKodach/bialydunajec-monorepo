package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.*


internal sealed class CampBusSeat(protected val currentTimeProvider: TimeProvider, val uncommittedEvents: List<SeatEvent>, val version: AggregateVersion) : AggregateRoot<SeatId> {

    fun replayEvent(event: SeatEvent) = applyEvent(event, EventApplyingMode.REPLAY_HISTORY)

    fun applyEvent(event: SeatEvent, mode: EventApplyingMode = EventApplyingMode.DEFAULT): CampBusSeat =
            composeOf(
                    when (mode) {
                        EventApplyingMode.APPLY_NEW_CHANGE -> listOf<SeatEvent>(*uncommittedEvents.toTypedArray(), event)
                        EventApplyingMode.REPLAY_HISTORY -> uncommittedEvents
                    },
                    event,
                    version.increase()
            )

    fun handle(command: SeatCommand): CampBusSeat {
        if (this.version !== command.aggregateVersion) {
            throw AggregateVersionMismatchException(version, command.aggregateVersion)
        }
        return process(command)
    }

    protected abstract fun process(command: SeatCommand): CampBusSeat

    protected abstract fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): CampBusSeat

    override fun toString() = "Seat(version=$version)"

    companion object {
        fun newInstance(currentTimeProvider: TimeProvider): CampBusSeat = Uninitialized(currentTimeProvider, emptyList(), AggregateVersion.ZERO)
    }

    class Uninitialized(currentTimeProvider: TimeProvider, events: List<SeatEvent>, version: AggregateVersion) : CampBusSeat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.AddSeatForCourse -> applyEvent(SeatEvent.SeatAddedForCourse(command.aggregateId, version, currentTimeProvider(), command.campBusCourseId))
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): CampBusSeat =
                when (lastEvent) {
                    is SeatEvent.SeatAddedForCourse -> Free(currentTimeProvider, lastEvent.aggregateId, lastEvent.campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

    }

    class Free(currentTimeProvider: TimeProvider, val seatId: SeatId, val campBusCourseId: CampBusCourseId, events: List<SeatEvent>, version: AggregateVersion) : CampBusSeat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): CampBusSeat =
                when (command) {
                    is SeatCommand.ReserveSeat -> applyEvent(SeatEvent.SeatReservedForPassenger(command.aggregateId, version, currentTimeProvider(), campBusCourseId, command.passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, null))
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): CampBusSeat =
                when (lastEvent) {
                    is SeatEvent.SeatReservedForPassenger -> Reserved(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, lastEvent.passengerId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

        override fun toString() = "Free(seatId=$seatId, campBusCourseId=$campBusCourseId, version=$version)"


    }


    class Reserved(currentTimeProvider: TimeProvider, val seatId: SeatId, val campBusCourseId: CampBusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, version: AggregateVersion) : CampBusSeat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): CampBusSeat =
                when (command) {
                    is SeatCommand.ConfirmReservation -> applyEvent(SeatEvent.SeatReservationConfirmed(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.CancelReservation -> applyEvent(SeatEvent.SeatReservationCancelled(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): CampBusSeat =
                when (lastEvent) {
                    is SeatEvent.SeatReservationCancelled -> Free(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatReservationConfirmed -> Occupied(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, lastEvent.passengerId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }


        override fun toString() = "Free(seatId=$seatId, campBusCourseId=$campBusCourseId, passengerId=$passengerId, version=$version)"

    }

    class Occupied(currentTimeProvider: TimeProvider, val seatId: SeatId, val campBusCourseId: CampBusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, version: AggregateVersion) : CampBusSeat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): CampBusSeat =
                when (command) {
                    is SeatCommand.ReleaseSeat -> applyEvent(SeatEvent.SeatReleased(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): CampBusSeat =
                when (lastEvent) {
                    is SeatEvent.SeatReleased -> Free(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

        override fun toString() = "Free(seatId=$seatId, campBusCourseId=$campBusCourseId, passengerId=$passengerId, version=$version)"

    }

    class Removed(currentTimeProvider: TimeProvider, val seatId: SeatId, val campBusCourseId: CampBusCourseId, events: List<SeatEvent>, version: AggregateVersion) : CampBusSeat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): CampBusSeat =
                throw UnprocessableCommandException(command, this)

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): CampBusSeat =
                this

        override fun toString() = "Free(seatId=$seatId, campBusCourseId=$campBusCourseId, version=$version)"


    }


}


class UnprocessableCommandException(val command: Command<*>, val aggregate: AggregateRoot<*>)
    : IllegalStateException("Command: <$command> cannot be processed by aggregate root: <$aggregate>!")

interface StateMachineAggregate
interface EventSourcedAggregate

