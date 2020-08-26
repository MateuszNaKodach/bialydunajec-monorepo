package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import java.util.*
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class EntityId(val entityId: String = defaultValue()) : Identifier<String> {
    override fun toString() = entityId

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityId

        if (entityId != other.entityId) return false

        return true
    }

    override fun hashCode(): Int {
        return entityId.hashCode()
    }

    override fun getIdentifierValue(): String = entityId

    companion object {
        fun defaultValue() = UUID.randomUUID().toString()
    }
}
