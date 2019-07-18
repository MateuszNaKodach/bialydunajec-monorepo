package org.bialydunajec.eventsourcing.domain

import java.util.*

open class AggregateId(private val id: String) {

    companion object {
        private const val UNDEFINED = "UNDEFINED"
        fun undefined() = AggregateId(UNDEFINED)
        fun new() = AggregateId(UUID.randomUUID().toString())
    }

    fun isUndefined() = id == UNDEFINED

    override fun toString() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}