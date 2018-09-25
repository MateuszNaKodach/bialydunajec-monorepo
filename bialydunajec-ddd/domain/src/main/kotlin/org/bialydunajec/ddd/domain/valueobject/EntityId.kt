package org.bialydunajec.ddd.domain.valueobject

import java.util.*
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class EntityId(val entityId: String = UUID.randomUUID().toString()) : Identifier{
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
}