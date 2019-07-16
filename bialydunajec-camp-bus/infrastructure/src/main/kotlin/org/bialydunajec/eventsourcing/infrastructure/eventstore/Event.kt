package org.bialydunajec.eventsourcing.infrastructure.eventstore

import java.util.*

interface Event<DATA> {
    val eventStreamId: UUID
    val data: DATA
    val metadata: EventMetadata
}