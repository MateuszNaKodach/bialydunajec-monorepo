package org.bialydunajec.eventsourcing.infrastructure.eventstore.engine.inmemory

import assertk.assertThat
import assertk.assertions.hasClass
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamName
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.EventStreamVersionIsNotAsExpected
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.ExpectedEventStreamVersion
import org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine.inmemory.InMemoryEventStorageEngine
import org.bialydunajec.eventsourcing.infrastructure.eventstore.serializer.JacksonEventSerializer
import org.bialydunajec.eventsourcing.testfixtures.SampleAggregateId
import org.bialydunajec.eventsourcing.testfixtures.SampleDomainEvent
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.Instant

internal class ExpectedEventStreamVersionSpecification : Spek({

    val currentTime = { Instant.now() }
    val eventSerializer = JacksonEventSerializer()

    describe("Feature: Event Stream expected version") {

        context("Given: Event Stream which not exists") {

            val eventStorageEngine: EventStorageEngine by memoized { InMemoryEventStorageEngine(eventSerializer) }
            val aggregateId = SampleAggregateId("not-exists-event-stream-name")
            val domainEvent: SampleDomainEvent = SampleDomainEvent.WithNoAdditionalValues(
                    aggregateId,
                    currentTime()
            )

            describe("When: Append event to the stream and expect 'None' Event Stream version") {

                it("Then: Event should be stored") {
                    assertThat {
                        eventStorageEngine.appendDomainEvent(EventStreamName(aggregateId.toString()), domainEvent, ExpectedEventStreamVersion.None)
                    }.doesNotThrowAnyException()
                }

            }

            describe("When: Append event to the stream and expect 'Any' Event Stream version") {

                it("Then: Event should be stored") {
                    assertThat {
                        eventStorageEngine.appendDomainEvent(EventStreamName(aggregateId.toString()), domainEvent, ExpectedEventStreamVersion.Any)
                    }.doesNotThrowAnyException()
                }

            }

            describe("When: Append event to the stream and expect 0 Event Stream version") {

                it("Then: Event should not be stored") {
                    assertThat {
                        eventStorageEngine.appendDomainEvent(EventStreamName(aggregateId.toString()), domainEvent, ExpectedEventStreamVersion.Zero)
                    }.thrownError {
                        hasClass(EventStreamVersionIsNotAsExpected::class)
                    }
                }

            }

        }


        context("Given: Event Stream which version is 0 (contains 1 Event)") {

            val aggregateId = SampleAggregateId("not-exists-event-stream-name")

            val eventStorageEngine: EventStorageEngine by memoized {
                InMemoryEventStorageEngine(eventSerializer)
                        .apply {
                            appendDomainEvent(
                                    EventStreamName(aggregateId.toString()),
                                    SampleDomainEvent.WithNoAdditionalValues(
                                            aggregateId,
                                            currentTime()
                                    ),
                                    ExpectedEventStreamVersion.None
                            )
                        }
            }

            describe("When: Append event to the stream and expect 'None' Event Stream version") {

                it("Then: Event should not be stored") {
                    assertThat {
                        eventStorageEngine.appendDomainEvent(
                                EventStreamName(aggregateId.toString()),
                                SampleDomainEvent.WithNoAdditionalValues(
                                        aggregateId,
                                        currentTime()
                                ),
                                ExpectedEventStreamVersion.None
                        )
                    }.thrownError {
                        hasClass(EventStreamVersionIsNotAsExpected::class)
                    }
                }

            }

            describe("When: Append event to the stream and expect 0 Event Stream version") {

                it("Then: Event should be stored") {
                    assertThat {
                        eventStorageEngine.appendDomainEvent(
                                EventStreamName(aggregateId.toString()),
                                SampleDomainEvent.WithNoAdditionalValues(
                                        aggregateId,
                                        currentTime()
                                ),
                                ExpectedEventStreamVersion.Zero
                        )
                    }.doesNotThrowAnyException()
                }

            }


        }


    }

})
