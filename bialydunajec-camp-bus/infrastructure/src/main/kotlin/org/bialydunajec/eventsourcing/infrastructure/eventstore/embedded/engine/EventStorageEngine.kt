package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.mongodb.DomainEventToMongoDbDocumentMapper

internal interface EventStorageEngine {

    fun appendEvents(domainEvents: List<DomainEvent<*>>) =
            domainEvents.map { toStoredDomainEventEntry(it) }

    fun appendEvents(domainEvents: List<StoredDomainEventEntry>)


    private fun toStoredDomainEventEntry(event: DomainEvent<*>) =
            event.let {
                val aggregateName: String = it::aggregateType.get().canonicalName
                StoredDomainEventEntry(
                        it.aggregateId.toString(),
                        aggregateName,
                        it.aggregateVersion.toLong(),
                        it.occurredAt,
                        eventSerializer.serialize(it),
                        it.javaClass.canonicalName,
                        "",
                        it.domainEventId.toString(),
                        DomainEventToMongoDbDocumentMapper.toEventName(it::class.simpleName),
                        1,
                        it.eventStreamType.canonicalName
                )
            }


    fun <EventType : DomainEvent<*>> readEvents(domainEventType: Class<EventType>, aggregateId: AggregateId): List<DomainEvent<*>>
}
