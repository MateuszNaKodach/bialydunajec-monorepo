package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import java.util.*

open class AggregateId(
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
