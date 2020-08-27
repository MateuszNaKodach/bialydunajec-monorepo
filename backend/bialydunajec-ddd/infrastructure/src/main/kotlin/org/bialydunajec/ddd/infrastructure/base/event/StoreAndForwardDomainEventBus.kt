package org.bialydunajec.ddd.infrastructure.base.event

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Identifier
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

open class StoreAndForwardDomainEventBus(private val delegate: DomainEventBus, private val eventsStorage: EventsStorage = InMemoryEventsStorage()) : DomainEventBus {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun publish(domainEvent: DomainEvent<*>) {
        eventsStorage.save(domainEvent)
    }

    @Scheduled(fixedRate = 100L)
    @Transactional
    open fun publishPeriodically() {
        val domainEvent = eventsStorage.toPublish()
        domainEvent?.let {
            delegate.publish(it)
            eventsStorage.published(it)
        }
    }

}

interface EventsStorage {
    fun save(event: DomainEvent<*>)
    fun toPublish(): DomainEvent<*>?
    fun published(event: DomainEvent<*>)
}

class InMemoryEventsStorage(private val keepStored: Boolean = true) : EventsStorage {

    private val publishQueue = ConcurrentLinkedQueue<DomainEvent<*>>()
    private val stored = ConcurrentHashMap<Identifier<*>, List<DomainEvent<*>>>()

    override fun save(event: DomainEvent<*>) {
        publishQueue.offer(event)
        if (keepStored) {
            stored.computeIfPresent(event.aggregateId) { _, events: List<DomainEvent<*>> -> events.plus(event) }
            stored.putIfAbsent(event.aggregateId, listOf(event))
        }
    }

    override fun toPublish(): DomainEvent<*>? {
        return publishQueue.peek()
    }

    override fun published(event: DomainEvent<*>) {
        publishQueue.remove(event)
    }
}

