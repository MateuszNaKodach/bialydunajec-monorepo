package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer

internal abstract class AbstractEventStorageEngine(override val eventSerializer: EventSerializer) : EventStorageEngine {

    override fun appendDomainEvent(domainEvent: DomainEvent<*>) =
            storeEvent(toStoreDomainEventEntry(domainEvent))

    abstract fun storeEvent(domainEvent: StoreDomainEventEntry)

    private fun toStoreDomainEventEntry(event: DomainEvent<*>) =
            event.let {
                val aggregateName: String = it::aggregateType.get().canonicalName
                StoreDomainEventEntry(
                        it.aggregateId.toString(),
                        aggregateName,
                        it.aggregateVersion.toLong(),
                        it.occurredAt,
                        eventSerializer.serialize(it),
                        it.javaClass.canonicalName,
                        "",
                        it.domainEventId.toString(),
                        toEventName(it::class.simpleName),
                        1,
                        it.eventStreamType.canonicalName
                )
            }

    fun <EventType : DomainEvent<*>> extractDomainEvents(payloadClass: Class<EventType>, storedEvents: List<StoreDomainEventEntry>) =
            storedEvents.map { extractDomainEvent(payloadClass, it) }

    private fun <EventType : DomainEvent<*>> extractDomainEvent(payloadClass: Class<EventType>, storedEvent: StoreDomainEventEntry) =
            eventSerializer.deserialize(payloadClass, storedEvent.serializedPayload)

    companion object {
        private fun toEventName(text: String?) =
                text
                        ?.foldIndexed("") { i, acc, c -> acc + if (i == 0) c else (if (c.isUpperCase()) "_$c" else c) }
                        ?.toUpperCase()
                        ?: "UNKNOWN"
    }

}
