package org.bialydunajec.campbus.events

import org.bialydunajec.campbus.domain.BusCourseId
import org.bialydunajec.campbus.domain.PassengerId
import org.bialydunajec.campbus.domain.SeatId
import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.domain.DomainEventId
import java.time.Instant


interface StoredEvent {
    val aggregateId: String
    val aggregateVersion: Long
    val domainEventId: String
    val occurredAt: Instant
    val eventVersion: Long
    val eventType: String
}

sealed class SeatEvent(override val aggregateId: String, override val aggregateVersion: Long, override val occurredAt: Instant, override val domainEventId: String) : DomainEvent<SeatId> {
    class SeatAddedForCourseV1(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservedForPassengerV1(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationConfirmedV1(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReservationCancelledV1(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatReleasedV1(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId) : SeatEvent(aggregateId, aggregateVersion, occurredAt)
    class SeatRemovedFromCourseV1(aggregateId: SeatId, aggregateVersion: AggregateVersion, occurredAt: Instant, val campBusCourseId: BusCourseId, val passengerId: PassengerId?) : SeatEvent(aggregateId, aggregateVersion, occurredAt)

    override fun toString() =
            "${this.javaClass.simpleName}(aggregateId=$aggregateId, aggregateVersion=$aggregateVersion, occuredAt=$occurredAt, domainEventId=$domainEventId)"

}

internal class DomainEventPayloadAdapter<D : DomainEvent<*>, S: StoredEvent<*>> {


}
