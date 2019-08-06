package org.bialydunajec.application.eventsourcing

class DomainMessageId(id: String) : Identifier(id) {

    companion object {
        fun from(id: Identifier) = DomainMessageId(id.toString())
    }

    override fun toString() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DomainMessageId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
