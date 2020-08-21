package org.bialydunajec.eventsourcing.infrastructure.eventbus

import org.bialydunajec.eventsourcing.domain.DomainEvent

interface EventBus {

    //TODO: Handling publication exception, for example if already published!
    fun publishAll(events: List<DomainEvent<*>>) =
            events.forEach { publish(it) }

    fun publish(event: DomainEvent<*>)

}
