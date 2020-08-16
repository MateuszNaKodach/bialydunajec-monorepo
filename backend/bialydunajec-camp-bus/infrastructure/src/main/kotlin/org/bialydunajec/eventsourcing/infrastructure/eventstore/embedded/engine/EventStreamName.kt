package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

class EventStreamName(private val name: String) {

    override fun toString() = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventStreamName

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


}