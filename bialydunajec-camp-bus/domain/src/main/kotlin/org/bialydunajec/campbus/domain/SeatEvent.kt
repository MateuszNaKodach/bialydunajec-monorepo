package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.domain.DomainEventId
import java.time.Instant

sealed class SeatEvent(
        aggregateId: SeatId,
        occurredAt: Instant
) : DomainEvent<SeatId>(aggregateId, DomainEventId(), occurredAt, Seat::class.java, SeatEvent::class.java) {

    class SeatAddedForCourse(
            aggregateId: SeatId,
            occurredAt: Instant,
            val campBusCourseId: BusCourseId
    ) : SeatEvent(aggregateId, occurredAt)

    class SeatReservedForPassenger(
            aggregateId: SeatId,
            occurredAt: Instant,
            val campBusCourseId: BusCourseId,
            val passengerId: PassengerId
    ) : SeatEvent(aggregateId, occurredAt)

    class SeatReservationConfirmed(
            aggregateId: SeatId,
            occurredAt: Instant,
            val campBusCourseId: BusCourseId,
            val passengerId: PassengerId
    ) : SeatEvent(aggregateId, occurredAt)

    class SeatReservationCancelled(
            aggregateId: SeatId,
            occurredAt: Instant,
            val campBusCourseId: BusCourseId,
            val passengerId: PassengerId
    ) : SeatEvent(aggregateId, occurredAt)

    class SeatReleased(
            aggregateId: SeatId,
            occurredAt: Instant,
            val campBusCourseId: BusCourseId,
            val passengerId: PassengerId
    ) : SeatEvent(aggregateId, occurredAt)

    class SeatRemovedFromCourse(
            aggregateId: SeatId,
            occurredAt: Instant,
            val campBusCourseId: BusCourseId,
            val passengerId: PassengerId?
    ) : SeatEvent(aggregateId, occurredAt)

    class SeatReservationFailed(
            aggregateId: SeatId,
            occurredAt: Instant,
            val campBusCourseId: BusCourseId,
            val passengerId: PassengerId,
            val violatedRule: SeatDomainRule
    ) : SeatEvent(aggregateId, occurredAt)

    override fun toString() =
            "${this.javaClass.simpleName}(aggregateId=$aggregateId, occuredAt=$occurredAt, domainEventId=$domainEventId)"

}
