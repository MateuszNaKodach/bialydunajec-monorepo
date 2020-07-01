package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

sealed class ExpectedEventStreamVersion {

    object None : ExpectedEventStreamVersion() {
        override fun toString(): String = "NONE"
    }

    open class Exactly(val version: Int) : ExpectedEventStreamVersion() {
        override fun toString(): String = version.toString()
    }

    object Zero : ExpectedEventStreamVersion.Exactly(0)

    object One : ExpectedEventStreamVersion.Exactly(1)

    object Two : ExpectedEventStreamVersion.Exactly(2)

    object Any : ExpectedEventStreamVersion() {
        override fun toString(): String = "ANY"
    }

}
