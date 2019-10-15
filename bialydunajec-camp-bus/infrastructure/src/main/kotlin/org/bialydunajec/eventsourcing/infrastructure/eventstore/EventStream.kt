package org.bialydunajec.eventsourcing.infrastructure.eventstore

import java.util.*
import kotlin.reflect.KClass

interface EventStream {
    val aggreagateId: AggregateId
    val aggregateType: KClass<*>
    val events: List<Event<*>>
}


data class AggregateId(val id: String = UUID.randomUUID().toString())