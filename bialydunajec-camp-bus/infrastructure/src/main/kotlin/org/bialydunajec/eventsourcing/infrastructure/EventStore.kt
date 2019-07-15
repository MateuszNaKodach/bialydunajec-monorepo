package org.bialydunajec.eventsourcing.infrastructure

import java.util.*

class EventStore {
    class EventStream(val aggreagateId: UUID)
    class Event<DATA>(val eventStreamId: UUID, val data: DATA, val metadata: EventMetadata)
    class EventMetadata(val correlationId: UUID, val causationId: UUID)
}