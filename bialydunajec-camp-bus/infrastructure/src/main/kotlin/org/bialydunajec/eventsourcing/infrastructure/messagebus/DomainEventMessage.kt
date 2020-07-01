package org.bialydunajec.eventsourcing.infrastructure.messagebus

internal interface DomainEventMessage<PayloadType> : EventMessage<PayloadType> {

    val aggregateIdentifier: String
    val aggregateType: String
    val aggregateVersion: Long
    val eventVersion: Long
    val eventType: String

    val eventIdentifier: String
        get() = identifier
}
