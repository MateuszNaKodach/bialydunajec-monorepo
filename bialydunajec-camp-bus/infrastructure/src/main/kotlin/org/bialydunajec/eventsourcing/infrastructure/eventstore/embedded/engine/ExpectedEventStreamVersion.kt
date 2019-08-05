package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine

sealed class ExpectedEventStreamVersion {

    class None : ExpectedEventStreamVersion()
    class Exactly(val version: Int) : ExpectedEventStreamVersion()
    class Any : ExpectedEventStreamVersion()

}
