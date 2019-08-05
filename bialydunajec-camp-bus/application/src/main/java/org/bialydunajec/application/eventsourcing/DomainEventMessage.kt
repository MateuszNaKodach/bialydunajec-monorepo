package org.bialydunajec.application.eventsourcing

import org.bialydunajec.eventsourcing.domain.DomainEvent

internal class DomainEventMessage<DomainEventType : DomainEvent<*>>(
        val domainEvent: DomainEventType,
        val targetAggregateVersion: Long
)