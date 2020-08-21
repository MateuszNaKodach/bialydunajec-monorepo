package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer

internal abstract class AbstractEventStorageEngine(override val eventSerializer: EventSerializer) : EventStorageEngine {

    override fun appendDomainEvent(streamName: EventStreamName, domainEvent: DomainEvent<*>, expectedVersion: ExpectedEventStreamVersion) =
            storeEvent(streamName, toStoreDomainEventEntry(domainEvent), expectedVersion)

    abstract fun storeEvent(streamName: EventStreamName, domainEvent: StoreDomainEventEntry, expectedVersion: ExpectedEventStreamVersion)

    private fun toStoreDomainEventEntry(event: DomainEvent<*>) =
            event.let {
                val aggregateName: String = it::aggregateType.get().canonicalName
                StoreDomainEventEntry(
                        it.aggregateId.toString(),
                        aggregateName,
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

    @Suppress("UNCHECKED_CAST")
    fun <EventType : DomainEvent<*>> extractDomainEvent(payloadClass: Class<EventType>, storedEvent: StoreDomainEventEntry) =
            eventSerializer.deserialize(tryToLoadClass(storedEvent.payloadType) as Class<DomainEvent<*>>, storedEvent.serializedPayload) as EventType

    companion object {
        private fun toEventName(text: String?) =
                text
                        ?.foldIndexed("") { i, acc, c -> acc + if (i == 0) c else (if (c.isUpperCase()) "_$c" else c) }
                        ?.toUpperCase()
                        ?: "UNKNOWN"

        /**
         * Function supports loading Kotlin nested classes, which are used for domain events
         */
        private fun tryToLoadClass(name: String): Class<out Any> =
                try {
                    Class.forName(name)
                } catch (e: ClassNotFoundException) {
                    val nestedClassName = StringBuilder(name)
                            .apply {
                                setCharAt(name.lastIndexOf('.'), '$')
                            }.toString()
                    Class.forName(nestedClassName)
                }


    }

}

