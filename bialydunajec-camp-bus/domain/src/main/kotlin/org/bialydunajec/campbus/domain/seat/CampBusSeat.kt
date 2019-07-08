package org.bialydunajec.campbus.domain.seat

import java.time.Instant
import java.util.*

typealias TimeProvider = () -> Instant

internal sealed class Seat(protected val currentTimeProvider: TimeProvider, protected val occurredEvents: List<SeatEvent> = emptyList()) {

    protected fun occurredEventsWithNew(newEvent: SeatEvent) = listOf<SeatEvent>(*occurredEvents.toTypedArray(), newEvent)
    abstract fun handle(command: SeatCommand): Seat
    abstract fun applyEvent(event: SeatEvent, isReplay: Boolean = false): Seat

    companion object {
        fun newInstance(currentTimeProvider: TimeProvider): Seat = Initialized(currentTimeProvider);
    }

    class Initialized(currentTimeProvider: TimeProvider) : Seat(currentTimeProvider) {

        override fun handle(command: SeatCommand) =
                when (command) {
                    is SeatCommand.AddSeatForCourse -> applyEvent(SeatEvent.SeatAddedForCourse(command.aggregateId, currentTimeProvider(), command.campBusCourseId))
                    else -> throw IllegalStateException("Command cannot be handled!")
                }

        override fun applyEvent(event: SeatEvent, isReplay: Boolean): Seat {
            val events = if (!isReplay) occurredEventsWithNew(event) else occurredEvents
            return when (event) {
                is SeatEvent.SeatAddedForCourse -> Free(currentTimeProvider, event.campBusCourseId, events)
                else -> this
            }
        }

    }

    class Free(currentTimeProvider: TimeProvider, val campBusCourseId: CampBusCourseId, events: List<SeatEvent>) : Seat(currentTimeProvider, events) {

        override fun handle(command: SeatCommand): Seat =
                when (command) {
                    is SeatCommand.ReserveSeat -> applyEvent(SeatEvent.ReservedForPassenger(command.aggregateId, currentTimeProvider(), campBusCourseId, command.passenger))
                    else -> throw IllegalStateException("Command cannot be handled!")
                }

        override fun applyEvent(event: SeatEvent, isReplay: Boolean): Seat {
            val events = if (!isReplay) occurredEventsWithNew(event) else occurredEvents
            return when (event) {
                is SeatEvent.ReservedForPassenger -> Reserved(currentTimeProvider, event.passenger, events)
                else -> this
            }
        }

    }


    class Reserved(currentTimeProvider: TimeProvider, private val passenger: Passenger, events: List<SeatEvent>) : Seat(currentTimeProvider, events) {

        override fun handle(command: SeatCommand): Seat {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun applyEvent(event: SeatEvent, isReplay: Boolean): Seat {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }

    private class Occupied(currentTimeProvider: TimeProvider, events: List<SeatEvent>) : Seat(currentTimeProvider, events) {

    }

    private class Removed()

}

class Passenger(val aggregateId: PassengerId, val trackingCodeId: PersonalTrackingCodeId)

class PersonalTrackingCodeId(private val id: String = UUID.randomUUID().toString())
class PassengerId(val id: String = UUID.randomUUID().toString())
class CampBusCourseId(val id: String = UUID.randomUUID().toString())
class SeatId(val id: String = UUID.randomUUID().toString())
class EventId(val id: String = UUID.randomUUID().toString())


sealed class SeatCommand(val aggregateId: SeatId) {
    class AddSeatForCourse(aggregateId: SeatId, val campBusCourseId: CampBusCourseId) : SeatCommand(aggregateId)
    class ReserveSeat(aggregateId: SeatId, val passenger: Passenger) : SeatCommand(aggregateId)
    class CancelReservation(aggregateId: SeatId) : SeatCommand(aggregateId)
    class ConfirmReservation(aggregateId: SeatId) : SeatCommand(aggregateId)
    class RemoveSeatFromCourse(aggregateId: SeatId) : SeatCommand(aggregateId)
}


sealed class SeatEvent(val aggregateId: SeatId, val occurredAt: Instant, val eventId: EventId = EventId()) {
    class SeatAddedForCourse(aggregateId: SeatId, occurredAt: Instant, val campBusCourseId: CampBusCourseId) : SeatEvent(aggregateId, occurredAt)
    class ReservedForPassenger(aggregateId: SeatId, occurredAt: Instant, val campBusCourseId: CampBusCourseId, val passenger: Passenger) : SeatEvent(aggregateId, occurredAt)

}

