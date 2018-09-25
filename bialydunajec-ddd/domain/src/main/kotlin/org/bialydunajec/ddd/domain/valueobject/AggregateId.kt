package org.bialydunajec.ddd.domain.valueobject

import java.util.*
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AggregateId(val aggregateId: String = UUID.randomUUID().toString()) : Identifier{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateId

        if (aggregateId != other.aggregateId) return false

        return true
    }

    override fun hashCode(): Int {
        return aggregateId.hashCode()
    }
}