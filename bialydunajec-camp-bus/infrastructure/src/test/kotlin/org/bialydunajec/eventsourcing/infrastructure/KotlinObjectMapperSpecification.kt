package org.bialydunajec.eventsourcing.infrastructure

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.fasterxml.jackson.databind.ObjectMapper
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.infrastructure.eventstore.serializer.eventSerializerConfig
import org.bialydunajec.eventsourcing.testfixtures.AnotherSampleAggregateId
import org.bialydunajec.eventsourcing.testfixtures.SampleAggregateId
import org.bialydunajec.eventsourcing.testfixtures.SampleDomainEvent
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.time.Instant

internal class KotlinObjectMapperSpecification : Spek({

    Feature("ObjectMapper configuration DomainEvent JSON serialization and deserialization") {

        val objectMapper: ObjectMapper by memoized { ObjectMapper().eventSerializerConfig() }

        Scenario("DomainEvent with no additional values") {

            lateinit var domainEvent: SampleDomainEvent
            lateinit var serializationResult: String

            Given("Example of Domain Event") {
                domainEvent = SampleDomainEvent.WithNoAdditionalValues(
                        SampleAggregateId("sample-id"),
                        AggregateVersion.ZERO,
                        Instant.now()
                )
            }

            When("Domain Event is serialized to JSON String") {
                serializationResult = objectMapper.writeValueAsString(domainEvent)
            }

            Then("JSON String should deserialize to given Domain Event") {
                val deserializedEvent = objectMapper.readValue(serializationResult, domainEvent::class.java)
                assertThat(deserializedEvent).isEqualTo(domainEvent)
                assertThat(deserializedEvent).apply {
                    prop(SampleDomainEvent.WithNoAdditionalValues::aggregateId).isEqualTo(domainEvent.aggregateId)
                    prop(SampleDomainEvent.WithNoAdditionalValues::aggregateVersion).isEqualTo(domainEvent.aggregateVersion)
                    prop(SampleDomainEvent.WithNoAdditionalValues::occurredAt).isEqualTo(domainEvent.occurredAt)
                }
            }

        }

        Scenario("DomainEvent with additional int value") {

            lateinit var domainEvent: SampleDomainEvent.WithAdditionalIntValue
            lateinit var serializationResult: String

            Given("Example of Domain Event") {
                domainEvent = SampleDomainEvent.WithAdditionalIntValue(
                        SampleAggregateId("sample-id"),
                        AggregateVersion.ZERO,
                        Instant.now(),
                        5
                )
            }

            When("Domain Event is serialized to JSON String") {
                serializationResult = objectMapper.writeValueAsString(domainEvent)
            }

            Then("JSON String should deserialize to given Domain Event") {
                val deserializedEvent = objectMapper.readValue(serializationResult, domainEvent::class.java)
                assertThat(deserializedEvent).isEqualTo(domainEvent)
                assertThat(deserializedEvent).apply {
                    prop(SampleDomainEvent.WithAdditionalIntValue::aggregateId).isEqualTo(domainEvent.aggregateId)
                    prop(SampleDomainEvent.WithAdditionalIntValue::aggregateVersion).isEqualTo(domainEvent.aggregateVersion)
                    prop(SampleDomainEvent.WithAdditionalIntValue::occurredAt).isEqualTo(domainEvent.occurredAt)
                    prop(SampleDomainEvent.WithAdditionalIntValue::intValue).isEqualTo(domainEvent.intValue)
                }
            }

        }

        Scenario("DomainEvent with additional another aggregate") {

            lateinit var domainEvent: SampleDomainEvent.WithAnotherAggregateId
            lateinit var serializationResult: String

            Given("Example of Domain Event") {
                domainEvent = SampleDomainEvent.WithAnotherAggregateId(
                        SampleAggregateId("sample-id"),
                        AggregateVersion.ZERO,
                        Instant.now(),
                        AnotherSampleAggregateId("another-sample-id")
                )
            }

            When("Domain Event is serialized to JSON String") {
                serializationResult = objectMapper.writeValueAsString(domainEvent)
            }

            Then("JSON String should deserialize to given Domain Event") {
                val deserializedEvent = objectMapper.readValue(serializationResult, domainEvent::class.java)
                assertThat(deserializedEvent).isEqualTo(domainEvent)
                assertThat(deserializedEvent).apply {
                    prop(SampleDomainEvent.WithAnotherAggregateId::aggregateId).isEqualTo(domainEvent.aggregateId)
                    prop(SampleDomainEvent.WithAnotherAggregateId::aggregateVersion).isEqualTo(domainEvent.aggregateVersion)
                    prop(SampleDomainEvent.WithAnotherAggregateId::occurredAt).isEqualTo(domainEvent.occurredAt)
                    prop(SampleDomainEvent.WithAnotherAggregateId::anotherAggregateId).isEqualTo(domainEvent.anotherAggregateId)
                }
            }

        }

    }

})






