package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.domain.DomainEventId
import java.time.Instant

sealed class SeatEvent(
        override val aggregateId: SeatId,
        override val aggregateVersion: AggregateVersion,
        override val occurredAt: Instant,
        override val domainEventId: DomainEventId = DomainEventId(),
        override val aggregateType: Class<*> = Seat::class.java,
        override val eventStreamType: Class<*> = SeatEvent::class.java) : DomainEvent<SeatId>() {
    class SeatAddedForCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservedForPassenger(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationConfirmed(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationCancelled(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReleased(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatRemovedFromCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId?) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class ReservedSeatReservationFailed(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, campBusCourseId: BusCourseId, passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)

    override fun toString() =
            "${this.javaClass.simpleName}(aggregateId=$aggregateId, aggregateVersion=$aggregateVersion, occuredAt=$occurredAt, domainEventId=$domainEventId)"

}
