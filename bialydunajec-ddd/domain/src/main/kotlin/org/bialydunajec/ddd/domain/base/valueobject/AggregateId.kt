package org.bialydunajec.ddd.domain.base.valueobject

import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AggregateId(
        @Column(unique = true, updatable = false, insertable = false)
        private val aggregateId: String = defaultValue()
) : Identifier<String> {

    override fun toString() = aggregateId

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

    override fun getIdentifierValue() = aggregateId

    companion object {
        fun defaultValue() = UUID.randomUUID().toString()
    }
}