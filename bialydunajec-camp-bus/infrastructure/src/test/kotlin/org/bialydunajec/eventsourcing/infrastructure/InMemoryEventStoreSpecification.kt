package org.bialydunajec.eventsourcing.infrastructure

import org.bialydunajec.eventsourcing.infrastructure.eventstore.inmemory.InMemoryEventStore
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

internal object InMemoryEventStoreSpecification : Spek({


    val eventStore by memoized { InMemoryEventStore() }

    Feature("Add event to stream") {

    }


})