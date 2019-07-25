package org.bialydunajec.eventsourcing.domain

import java.util.*

data class DomainEventId(private val id: String = UUID.randomUUID().toString()) {
    override fun toString() = id
}
