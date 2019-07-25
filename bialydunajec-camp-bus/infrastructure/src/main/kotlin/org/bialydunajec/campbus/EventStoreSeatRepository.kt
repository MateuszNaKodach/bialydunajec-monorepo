package org.bialydunajec.campbus

import com.github.nowakprojects.kttimetraveler.core.TimeProvider
import org.bialydunajec.campbus.domain.Seat
import org.bialydunajec.campbus.domain.SeatEvent
import org.bialydunajec.campbus.domain.SeatId
import org.bialydunajec.campbus.domain.SeatRepository
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventStore

internal class EventStoreSeatRepository(
        private val timeProvider: TimeProvider,
        private val eventStore: EventStore)
    : SeatRepository {

    override fun save(seat: Seat) {
        if (seat.aggregateId.isUndefined()) {
            throw IllegalArgumentException("Uninitialized aggregate cannot be saved!")
        }
        eventStore.publishAll(seat.changes)
    }

    override fun findById(seatId: SeatId): Seat? {
        val domainEvents = eventStore.readEvents(SeatEvent::class.java, seatId, timeProvider.instant)
        return when (domainEvents.isNotEmpty()) {
            true -> Seat.recreateFrom({ timeProvider.instant }, domainEvents)
            false -> null
        }
    }
}
