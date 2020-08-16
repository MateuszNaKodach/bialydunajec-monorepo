package org.bialydunajec.eventsourcing.infrastructure.eventstore

import java.util.*

interface EventMetadata {
    val correlationId: UUID
    val causationId: UUID
}