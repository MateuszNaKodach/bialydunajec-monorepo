package org.bialydunajec.eventsourcing.infrastructure.eventstore.engine.inmemory

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.hasClass
import assertk.assertions.isNotNull
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamName
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.ExpectedEventStreamVersion
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.inmemory.InMemoryEventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.exception.DomainEventAlreadyStoredException
import org.bialydunajec.eventsourcing.infrastructure.eventstore.serializer.JacksonEventSerializer
import org.bialydunajec.eventsourcing.testfixtures.SampleAggregateId
import org.bialydunajec.eventsourcing.testfixtures.SampleDomainEvent
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.time.Instant

internal object StoringAndReadingEventsByInMemoryStorageEngineSpecification : Spek({

    Feature("Storing and reading domain event by InMemoryEventStorageEngine") {

        val currentTime = { Instant.now() }
        val eventSerializer: EventSerializer by memoized { JacksonEventSerializer() }
        val eventStorageEngine: EventStorageEngine by memoized { InMemoryEventStorageEngine(eventSerializer) }

        Scenario("Store new event") {

            lateinit var domainEvent: SampleDomainEvent

            Given("New event to store") {
                domainEvent = SampleDomainEvent.WithNoAdditionalValues(
                        SampleAggregateId("sample-id"),
                        currentTime()
                )
            }

            When("Store the event") {
                eventStorageEngine.appendDomainEvent(EventStreamName(domainEvent.aggregateId.toString()), domainEvent, ExpectedEventStreamVersion.Any)
            }

            Then("The event should be readable from the storage") {
                val events = eventStorageEngine.readEvents(
                        SampleDomainEvent::class,
                        SampleAggregateId("sample-id"),
                        currentTime()
                )
                assertThat(events).containsOnly(domainEvent)
            }

        }

        Scenario("Try to store the same event which is already stored") {

            val domainEvent: SampleDomainEvent = SampleDomainEvent.WithNoAdditionalValues(
                    SampleAggregateId("sample-id"),
                    currentTime()
            )
            var thrownException: Exception? = null

            Given("Already stored event") {
                eventStorageEngine.appendDomainEvent(EventStreamName(domainEvent.aggregateId.toString()), domainEvent, ExpectedEventStreamVersion.Any)
            }

            When("Try to store the same event") {
                try {
                    eventStorageEngine.appendDomainEvent(EventStreamName(domainEvent.aggregateId.toString()), domainEvent, ExpectedEventStreamVersion.Any)
                } catch (e: Exception) {
                    thrownException = e
                }
            }

            Then("DomainEventAlreadyStored exception should be thrown") {
                assertThat(thrownException).isNotNull()
                        .hasClass(DomainEventAlreadyStoredException::class)
            }

        }

    }

})
