package org.bialydunajec.eventsourcing.domain

import java.util.*

class DomainEventId(id: String = UUID.randomUUID().toString()): Identifier(id){

    override fun toString() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DomainEventId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
