package org.bialydunajec.campbus.domain.seat

import java.time.Instant
import java.util.*

typealias TimeProvider = () -> Instant

enum class EventApplyingMode {
    APPLY_NEW_CHANGE,
    REPLAY_HISTORY;

    companion object {
        val DEFAULT: EventApplyingMode = APPLY_NEW_CHANGE
    }
}


interface StateMachineAggregate
interface EventSourcedAggregate

internal sealed class Seat(protected val currentTimeProvider: TimeProvider, val uncommittedEvents: List<SeatEvent>, val version: AggregateVersion) {

    fun replayEvent(event: SeatEvent) = applyEvent(event, EventApplyingMode.REPLAY_HISTORY)
    fun applyEvent(event: SeatEvent, mode: EventApplyingMode = EventApplyingMode.DEFAULT): Seat =
            composeOf(
                    when (mode) {
                        EventApplyingMode.APPLY_NEW_CHANGE -> listOf<SeatEvent>(*uncommittedEvents.toTypedArray(), event)
                        EventApplyingMode.REPLAY_HISTORY -> uncommittedEvents
                    },
                    event,
                    version.increase()
            )

    fun handle(command: SeatCommand): Seat {
        if (this.version !== command.aggregateVersion) {
            throw IllegalStateException("Invalid aggregate version! Actual: ${version.toLong()}, expected: ${command.aggregateVersion.toLong()}")
        }
        return process(command)
    }

    protected abstract fun process(command: SeatCommand): Seat
    protected abstract fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat

    companion object {
        fun newInstance(currentTimeProvider: TimeProvider): Seat = Uninitialized(currentTimeProvider, emptyList(), AggregateVersion.ZERO)
    }

    class Uninitialized(currentTimeProvider: TimeProvider, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.AddSeatForCourse -> applyEvent(SeatEvent.SeatAddedForCourse(command.aggregateId, version, currentTimeProvider(), command.campBusCourseId))
                    else -> throw IllegalStateException("Command cannot be handled!")
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatAddedForCourse -> Free(currentTimeProvider, lastEvent.campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

    }

    class Free(currentTimeProvider: TimeProvider, val campBusCourseId: CampBusCourseId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ReserveSeat -> applyEvent(SeatEvent.SeatReservedForPassenger(command.aggregateId, version, currentTimeProvider(), campBusCourseId, command.passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, null))
                    else -> throw IllegalStateException("Invalid command!")
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReservedForPassenger -> Reserved(currentTimeProvider, campBusCourseId, lastEvent.passengerId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

    }


    class Reserved(currentTimeProvider: TimeProvider, val campBusCourseId: CampBusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ConfirmReservation -> applyEvent(SeatEvent.SeatReservationConfirmed(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.CancelReservation -> applyEvent(SeatEvent.SeatReservationCancelled(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    else -> throw IllegalStateException("Invalid command!")
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReservationCancelled -> Free(currentTimeProvider, campBusCourseId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatReservationConfirmed -> Occupied(currentTimeProvider, campBusCourseId, lastEvent.passengerId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

    }

    private class Occupied(currentTimeProvider: TimeProvider, val campBusCourseId: CampBusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ReleaseSeat -> applyEvent(SeatEvent.SeatReleased(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    else -> throw IllegalStateException("Invalid command!")
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReleased -> Free(currentTimeProvider, campBusCourseId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

    }

    private class Removed(currentTimeProvider: TimeProvider, val campBusCourseId: CampBusCourseId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): Seat =
                throw IllegalStateException("Invalid command!")

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                this

    }

}

//class Passenger(val aggregateId: PassengerId, val trackingCodeId: PersonalTrackingCodeId)

class PersonalTrackingCodeId(private val id: String = UUID.randomUUID().toString())
class PassengerId(val id: String = UUID.randomUUID().toString())
class CampBusCourseId(val id: String = UUID.randomUUID().toString())
class SeatId(val id: String = UUID.randomUUID().toString())
class EventId(val id: String = UUID.randomUUID().toString())


sealed class SeatCommand(val aggregateId: SeatId, val aggregateVersion: AggregateVersion) {
    class AddSeatForCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, val campBusCourseId: CampBusCourseId) : SeatCommand(aggregateId, aggregateVersion)
    class ReserveSeat(aggregateId: SeatId, aggregateVersion: AggregateVersion, val passengerId: PassengerId) : SeatCommand(aggregateId, aggregateVersion)
    class CancelReservation(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class ConfirmReservation(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class RemoveSeatFromCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class ReleaseSeat(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
}


sealed class SeatEvent(val aggregateId: SeatId, val aggregateVersion: AggregateVersion, val occurredAt: Instant, val eventId: EventId = EventId()) {
    class SeatAddedForCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservedForPassenger(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationConfirmed(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationCancelled(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReleased(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatRemovedFromCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId?) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
}


data class AggregateVersion(private val version: Long) {
    companion object {
        val ZERO = AggregateVersion(0)
    }

    fun increase() = AggregateVersion(version + 1)

    fun toLong() = version
}


class EventStore {
    class EventStream(val aggreagateId: UUID)
    class Event<DATA>(val eventStreamId: UUID, val data: DATA, val metadata: EventMetadata)
    class EventMetadata(val correlationId: UUID, val causationId: UUID)
}
