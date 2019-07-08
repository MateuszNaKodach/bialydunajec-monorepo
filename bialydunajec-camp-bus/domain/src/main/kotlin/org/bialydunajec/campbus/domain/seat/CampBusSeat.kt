package org.bialydunajec.campbus.domain.seat

import java.time.Instant

//TODO: add functions which accepts current time, return event!!!
internal sealed class Seat(protected val currentTimeProvider: () -> Instant, protected val occuredEvents: List<SeatEvent> = emptyList()) {

    private class Free : Seat() {

        fun reserveFor(passenger: Passenger) {
            applyEvent(SeatEvent.ReservedForPassenger(passenger))
        }

        fun applyEvent(event: SeatEvent, isReplay: Boolean = false): Seat {
            val events = if (!isReplay) occuredEventsWithNew(event) else occuredEvents
            return when (event) {
                is SeatEvent.ReservedForPassenger -> Reserved(event.passenger, events)
                else -> this
            }
        }


        private fun occuredEventsWithNew(newEvent: SeatEvent) = listOf<SeatEvent>(*occuredEvents.toTypedArray(), newEvent)

    }

    private class Reserved(private val passenger: Passenger, events: List<SeatEvent>) : Seat(events) {


    }

    private class Occupied : Seat() {

    }

}

class Passenger {

}


sealed class SeatEvent {

    class ReservedForPassenger(val passenger: Passenger) : SeatEvent()

}
