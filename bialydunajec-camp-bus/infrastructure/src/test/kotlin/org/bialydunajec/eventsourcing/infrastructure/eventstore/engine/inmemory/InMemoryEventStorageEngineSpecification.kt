package org.bialydunajec.eventsourcing.infrastructure.eventstore.engine.inmemory

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.hasClass
import assertk.assertions.isNotNull
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.infrastructure.eventstore.EventSerializer
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.inmemory.InMemoryEventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.exception.DomainEventAlreadyStoredException
import org.bialydunajec.eventsourcing.infrastructure.eventstore.serializer.JacksonEventSerializer
import org.bialydunajec.eventsourcing.testfixtures.SampleAggregateId
import org.bialydunajec.eventsourcing.testfixtures.SampleDomainEvent
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.time.Instant

internal object InMemoryEventStorageEngineSpecification : Spek({

    Feature("Storing domain event in memory by InMemoryEventStorageEngine") {

        val eventSerializer: EventSerializer by memoized { JacksonEventSerializer() }
        val eventStorageEngine: EventStorageEngine by memoized { InMemoryEventStorageEngine(eventSerializer) }

        Scenario("Store new event") {

            lateinit var domainEvent: SampleDomainEvent

            Given("New event to store") {
                domainEvent = SampleDomainEvent.WithNoAdditionalValues(
                        SampleAggregateId("sample-id"),
                        AggregateVersion.ZERO,
                        Instant.now()
                )
            }

            When("The event is stored") {
                eventStorageEngine.appendDomainEvent(domainEvent)
            }

            Then("None exception should be thrown") {
            }

        }

        Scenario("Try to store the same event which is already stored") {

            val domainEvent: SampleDomainEvent = SampleDomainEvent.WithNoAdditionalValues(
                    SampleAggregateId("sample-id"),
                    AggregateVersion.ZERO,
                    Instant.now()
            )
            var thrownException: Exception? = null

            Given("Already stored event") {
                eventStorageEngine.appendDomainEvent(domainEvent)
            }

            When("Try to store the same event") {
                try {
                    eventStorageEngine.appendDomainEvent(domainEvent)
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

    Feature("Reading domain event from memory by InMemoryEventStorageEngine") {

        val currentTime = { Instant.now() }
        val eventSerializer: EventSerializer by memoized { JacksonEventSerializer() }
        val eventStorageEngine: EventStorageEngine by memoized { InMemoryEventStorageEngine(eventSerializer) }

        Scenario("Read events after store one") {

            lateinit var domainEvent: SampleDomainEvent

            Given("New event to store") {
                domainEvent = SampleDomainEvent.WithNoAdditionalValues(
                        SampleAggregateId("sample-id"),
                        AggregateVersion.ZERO,
                        currentTime()
                )
            }

            When("The event is stored") {
                eventStorageEngine.appendDomainEvent(domainEvent)
            }

            Then("the event should be readable from the storage") {
                val events = eventStorageEngine.readEvents(SampleDomainEvent::class, SampleAggregateId("sample-id"), currentTime())
                assertThat(events).containsOnly(domainEvent)
            }

        }

    }


})
