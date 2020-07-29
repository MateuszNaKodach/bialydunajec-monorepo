package org.bialydunajec.eventsourcing.infrastructure.eventstore.exception

open class EventStoreException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
