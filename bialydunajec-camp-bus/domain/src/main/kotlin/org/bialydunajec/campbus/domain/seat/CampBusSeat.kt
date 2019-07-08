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


internal sealed class Seat(protected val currentTimeProvider: TimeProvider, val occurredEvents: List<SeatEvent>, val version: AggregateVersion) {

    fun replayEvent(event: SeatEvent) = applyEvent(event, EventApplyingMode.REPLAY_HISTORY)
    fun applyEvent(event: SeatEvent, mode: EventApplyingMode = EventApplyingMode.DEFAULT): Seat {
        fun occurredEventsWithNew(newEvent: SeatEvent) = listOf<SeatEvent>(*occurredEvents.toTypedArray(), newEvent)
        val events = when (mode) {
            EventApplyingMode.APPLY_NEW_CHANGE -> occurredEventsWithNew(event)
            EventApplyingMode.REPLAY_HISTORY -> occurredEvents
        }
        return composeOf(events, event, version.increase())
    }

    abstract fun handle(command: SeatCommand): Seat //TODO: Add veryfing aggregate version!
    protected abstract fun composeOf(history: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat

    companion object {
        fun newInstance(currentTimeProvider: TimeProvider): Seat = Initialized(currentTimeProvider, emptyList(), AggregateVersion.ZERO)
    }

    class Initialized(currentTimeProvider: TimeProvider, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun handle(command: SeatCommand) =
                when (command) {
                    is SeatCommand.AddSeatForCourse -> applyEvent(SeatEvent.SeatAddedForCourse(command.aggregateId, version, currentTimeProvider(), command.campBusCourseId))
                    else -> throw IllegalStateException("Command cannot be handled!")
                }

        override fun composeOf(history: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat {
            return when (lastEvent) {
                is SeatEvent.SeatAddedForCourse -> Free(currentTimeProvider, lastEvent.campBusCourseId, history, nextVersion)
                else -> this
            }
        }

    }

    class Free(currentTimeProvider: TimeProvider, val campBusCourseId: CampBusCourseId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun handle(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ReserveSeat -> applyEvent(SeatEvent.SeatReservedForPassenger(command.aggregateId, version, currentTimeProvider(), campBusCourseId, command.passengerId))
                    else -> throw IllegalStateException("Invalid command!")
                }

        override fun composeOf(history: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat {
            return when (lastEvent) {
                is SeatEvent.SeatReservedForPassenger -> Reserved(currentTimeProvider, campBusCourseId, lastEvent.passengerId, history, nextVersion)
                else -> this
            }
        }

    }


    class Reserved(currentTimeProvider: TimeProvider, val campBusCourseId: CampBusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun handle(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ConfirmReservation -> applyEvent(SeatEvent.SeatReservationConfirmed(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.CancelReservation -> applyEvent(SeatEvent.SeatReservationCancelled(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    else -> throw IllegalStateException("Invalid command!")
                }

        override fun composeOf(history: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private class Occupied(currentTimeProvider: TimeProvider, val campBusCourseId: CampBusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

    }

    private class Removed() {

    }

}

//class Passenger(val aggregateId: PassengerId, val trackingCodeId: PersonalTrackingCodeId)

class PersonalTrackingCodeId(private val id: String = UUID.randomUUID().toString())
class PassengerId(val id: String = UUID.randomUUID().toString())
class CampBusCourseId(val id: String = UUID.randomUUID().toString())
class SeatId(val id: String = UUID.randomUUID().toString())
class EventId(val id: String = UUID.randomUUID().toString())


sealed class SeatCommand(val aggregateId: SeatId, aggregateVersion: AggregateVersion) {
    class AddSeatForCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, val campBusCourseId: CampBusCourseId) : SeatCommand(aggregateId, aggregateVersion)
    class ReserveSeat(aggregateId: SeatId, aggregateVersion: AggregateVersion, val passengerId: PassengerId) : SeatCommand(aggregateId, aggregateVersion)
    class CancelReservation(aggregateId: SeatIdaggregateVersion: AggregateVersion, ) : SeatCommand(aggregateId, aggregateVersion)
    class ConfirmReservation(aggregateId: SeatIdaggregateVersion: AggregateVersion, ) : SeatCommand(aggregateId, aggregateVersion)
    class RemoveSeatFromCourse(aggregateId: SeatIdaggregateVersion: AggregateVersion, ) : SeatCommand(aggregateId, aggregateVersion)
}


sealed class SeatEvent(val aggregateId: SeatId, aggregateVersion: AggregateVersion, val occurredAt: Instant, val eventId: EventId = EventId()) {
    class SeatAddedForCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservedForPassenger(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationConfirmed(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationCancelled(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
}


data class AggregateVersion(private val version: Long) {
    companion object {
        val ZERO = AggregateVersion(0)
    }

    fun increase() = AggregateVersion(version + 1)

    fun toLong() = version
}
