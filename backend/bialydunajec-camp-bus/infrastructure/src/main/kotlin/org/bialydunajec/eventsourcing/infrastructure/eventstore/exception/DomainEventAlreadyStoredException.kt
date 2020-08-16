package org.bialydunajec.eventsourcing.infrastructure.eventstore.exception

class DomainEventAlreadyStoredException(eventIdentifier: String, cause: Throwable? = null)
    : EventStoreException("Event with eventIdentifier=$eventIdentifier is already stored!")

