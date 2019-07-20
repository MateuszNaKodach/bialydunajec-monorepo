package org.bialydunajec.campbus

import arrow.mtl.instances.list.functorFilter.filter
import org.bialydunajec.campbus.domain.Seat
import org.bialydunajec.campbus.domain.SeatEvent
import org.bialydunajec.campbus.domain.SeatId
import org.bialydunajec.campbus.domain.SeatRepository
import org.bialydunajec.eventsourcing.domain.TimeProvider
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

typealias EventPublisher = (SeatEvent) -> Unit

internal class EventSourcedSeatRepository(private val timeProvider: TimeProvider, private val eventPublisher: EventPublisher?) : SeatRepository {

    private val seats = ConcurrentHashMap<SeatId, List<SeatEvent>>()

    override fun save(seat: Seat) {
        val uncommittedChanges = seat.changes
        val persistedChanges = getEventsUpTo(seat.aggregateId, timeProvider())
        seats[seat.aggregateId] = persistedChanges.plus(uncommittedChanges)
        uncommittedChanges.forEach { eventPublisher?.invoke(it) }
    }

    override fun findById(seatId: SeatId): Seat? =
        when(seats.containsKey(seatId)){
            true -> Seat.recreateFrom(timeProvider,getEventsUpTo(seatId,timeProvider()))
            false -> null
        }


    private fun getEventsUpTo(seatId: SeatId, timestamp: Instant) =
            (seats[seatId] ?: emptyList()).filter { !it.occurredAt.isAfter(timestamp) }
}
