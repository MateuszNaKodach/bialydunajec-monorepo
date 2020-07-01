package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

sealed class EventStreamVersion {

    object None : EventStreamVersion() {
        override fun toString(): String = "NONE"
    }

    data class Exactly(val version: Int) : EventStreamVersion() {
        override fun toString(): String = version.toString()
    }
}


