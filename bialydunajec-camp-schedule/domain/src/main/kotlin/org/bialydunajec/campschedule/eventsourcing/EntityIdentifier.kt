package org.bialydunajec.campschedule.eventsourcing

import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import java.util.*

abstract class EntityIdentifier(
        private val entityId: String = EntityIdentifier.defaultValue()
) : Identifier<String> {

    override fun toString() = entityId

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityIdentifier

        if (entityId != other.entityId) return false

        return true
    }

    override fun hashCode(): Int {
        return entityId.hashCode()
    }

    override fun getIdentifierValue() = entityId

    companion object {
        fun defaultValue() = UUID.randomUUID().toString()
    }
}
