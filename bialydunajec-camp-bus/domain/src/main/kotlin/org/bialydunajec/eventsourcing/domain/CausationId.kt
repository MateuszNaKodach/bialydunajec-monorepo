package org.bialydunajec.eventsourcing.domain

class CausationId(id: String) : Identifier(id) {

    companion object {
        fun from(id: Identifier) = CausationId(id.toString())
    }

    override fun toString() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CausationId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
