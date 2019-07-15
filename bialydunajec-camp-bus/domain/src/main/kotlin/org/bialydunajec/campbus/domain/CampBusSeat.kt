package org.bialydunajec.campbus.domain

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

class UnprocessableCommandException(val command: Command<*>, val aggregate: AggregateRoot<*>) : IllegalStateException("Command: <$command> cannot be processed by aggregate root: <$aggregate>!")

interface AggregateRoot<AggregateIdType : AggregateId>

internal sealed class Seat(protected val currentTimeProvider: TimeProvider, val uncommittedEvents: List<SeatEvent>, val version: AggregateVersion) : AggregateRoot<SeatId> {

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

    override fun toString() = "Seat(version=$version)"

    companion object {
        fun newInstance(currentTimeProvider: TimeProvider): Seat = Uninitialized(currentTimeProvider, emptyList(), AggregateVersion.ZERO)
    }

    class Uninitialized(currentTimeProvider: TimeProvider, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand) =
                when (command) {
                    is SeatCommand.AddSeatForCourse -> applyEvent(SeatEvent.SeatAddedForCourse(command.aggregateId, version, currentTimeProvider(), command.campBusCourseId))
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatAddedForCourse -> Free(currentTimeProvider, lastEvent.aggregateId, lastEvent.campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

    }

    class Free(currentTimeProvider: TimeProvider, val seatId: SeatId, val campBusCourseId: CampBusCourseId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ReserveSeat -> applyEvent(SeatEvent.SeatReservedForPassenger(command.aggregateId, version, currentTimeProvider(), campBusCourseId, command.passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, null))
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReservedForPassenger -> Reserved(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, lastEvent.passengerId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

        override fun toString() = "Free(seatId=$seatId, campBusCourseId=$campBusCourseId, version=$version)"


    }


    class Reserved(currentTimeProvider: TimeProvider, val seatId: SeatId, val campBusCourseId: CampBusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ConfirmReservation -> applyEvent(SeatEvent.SeatReservationConfirmed(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.CancelReservation -> applyEvent(SeatEvent.SeatReservationCancelled(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReservationCancelled -> Free(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatReservationConfirmed -> Occupied(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, lastEvent.passengerId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }


        override fun toString() = "Free(seatId=$seatId, campBusCourseId=$campBusCourseId, passengerId=$passengerId, version=$version)"

    }

    class Occupied(currentTimeProvider: TimeProvider, val seatId: SeatId, val campBusCourseId: CampBusCourseId, private val passengerId: PassengerId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ReleaseSeat -> applyEvent(SeatEvent.SeatReleased(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    is SeatCommand.RemoveSeatFromCourse -> applyEvent(SeatEvent.SeatRemovedFromCourse(command.aggregateId, version, currentTimeProvider(), campBusCourseId, passengerId))
                    else -> throw UnprocessableCommandException(command, this)
                }

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                when (lastEvent) {
                    is SeatEvent.SeatReleased -> Free(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    is SeatEvent.SeatRemovedFromCourse -> Removed(currentTimeProvider, lastEvent.aggregateId, campBusCourseId, uncommitedHistory, nextVersion)
                    else -> this
                }

        override fun toString() = "Free(seatId=$seatId, campBusCourseId=$campBusCourseId, passengerId=$passengerId, version=$version)"

    }

    class Removed(currentTimeProvider: TimeProvider, val seatId: SeatId, val campBusCourseId: CampBusCourseId, events: List<SeatEvent>, version: AggregateVersion) : Seat(currentTimeProvider, events, version) {

        override fun process(command: SeatCommand): Seat =
                throw UnprocessableCommandException(command, this)

        override fun composeOf(uncommitedHistory: List<SeatEvent>, lastEvent: SeatEvent, nextVersion: AggregateVersion): Seat =
                this

        override fun toString() = "Free(seatId=$seatId, campBusCourseId=$campBusCourseId, version=$version)"


    }


}

//class Passenger(val aggregateId: PassengerId, val trackingCodeId: PersonalTrackingCodeId)

class PersonalTrackingCodeId(private val id: String = UUID.randomUUID().toString()) : AggregateId(id)
class PassengerId(val id: String = UUID.randomUUID().toString()) : AggregateId(id)
class CampBusCourseId(val id: String = UUID.randomUUID().toString()) : AggregateId(id)
class SeatId(val id: String = UUID.randomUUID().toString()) : AggregateId(id)
class DomainEventId(val id: String = UUID.randomUUID().toString())


open class AggregateId(private val id: String) {
    companion object {
        private const val UNDEFINED = "UNDEFINED"
        fun undefined() = AggregateId(UNDEFINED)
        fun new() = AggregateId(UUID.randomUUID().toString())
    }


    fun isUndefined() = id == UNDEFINED

    override fun toString() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}


sealed class SeatCommand(override val aggregateId: SeatId, override val aggregateVersion: AggregateVersion) : Command<SeatId> {
    class AddSeatForCourse(aggregateId: SeatId, val campBusCourseId: CampBusCourseId) : SeatCommand(aggregateId, AggregateVersion.ZERO)
    class ReserveSeat(aggregateId: SeatId, aggregateVersion: AggregateVersion, val passengerId: PassengerId) : SeatCommand(aggregateId, aggregateVersion)
    class CancelReservation(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class ConfirmReservation(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class RemoveSeatFromCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class ReleaseSeat(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)

    override fun toString() =
            "${this.javaClass.simpleName}(aggregateId=$aggregateId, aggregateVersion=$aggregateVersion)"

}


interface Command<AggregateIdType : AggregateId> {
    val aggregateId: SeatId
    val aggregateVersion: AggregateVersion
}

interface DomainEvent<AggregateIdType : AggregateId> {
    val aggregateId: AggregateIdType
    val aggregateVersion: AggregateVersion
    val domainEventId: DomainEventId
    val occurredAt: Instant
}

sealed class SeatEvent(override val aggregateId: SeatId, override val aggregateVersion: AggregateVersion, override val occurredAt: Instant, override val domainEventId: DomainEventId = DomainEventId()) : DomainEvent<SeatId> {
    class SeatAddedForCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservedForPassenger(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationConfirmed(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationCancelled(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReleased(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatRemovedFromCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passengerId: PassengerId?) : SeatEvent(aggregateId, aggregateVersion, occurredAt)

    override fun toString() =
            "${this.javaClass.simpleName}(aggregateId=$aggregateId, aggregateVersion=$aggregateVersion, occuredAt=$occurredAt, domainEventId=$domainEventId)"

}


class AggregateVersion(private val version: Long) {
    companion object {
        val ZERO = AggregateVersion(0)
    }

    fun increase() = AggregateVersion(version + 1)

    fun toLong() = version

    override fun toString(): String = version.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateVersion

        if (version != other.version) return false

        return true
    }

    override fun hashCode(): Int {
        return version.hashCode()
    }


}


interface StateMachineAggregate
interface EventSourcedAggregate

class EventStore {
    class EventStream(val aggreagateId: UUID)
    class Event<DATA>(val eventStreamId: UUID, val data: DATA, val metadata: EventMetadata)
    class EventMetadata(val correlationId: UUID, val causationId: UUID)
}
