package org.bialydunajec.eventsourcing.infrastructure

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.bialydunajec.campbus.domain.Seat
import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.domain.DomainEventId
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.time.Instant
import java.util.*

internal class KotlinObjectMapperSpecification : Spek({

    Feature("ObjectMapper configuration DomainEvent JSON serialization and deserialization") {

        val objectMapper: ObjectMapper by memoized {
            ObjectMapper().registerKotlinModule()
                    .registerModule(JavaTimeModule())
                    .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                    .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
        }

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


internal class SampleAggregateId(id: String = UUID.randomUUID().toString()) : AggregateId(id)
internal class AnotherSampleAggregateId(id: String = UUID.randomUUID().toString()) : AggregateId(id)
internal sealed class SampleDomainEvent(
        override val aggregateId: SampleAggregateId,
        override val aggregateVersion: AggregateVersion,
        override val occurredAt: Instant,
        override val domainEventId: DomainEventId = DomainEventId(),
        override val aggregateType: Class<*> = Seat::class.java,
        override val eventStreamType: Class<*> = SampleDomainEvent::class.java) : DomainEvent<SampleAggregateId>() {
    class WithNoAdditionalValues(aggregateId: SampleAggregateId, aggregateVersion: AggregateVersion, occurredAt: Instant) : SampleDomainEvent(aggregateId, aggregateVersion, occurredAt)
    class WithAdditionalIntValue(aggregateId: SampleAggregateId, aggregateVersion: AggregateVersion, occurredAt: Instant, val intValue: Int) : SampleDomainEvent(aggregateId, aggregateVersion, occurredAt)
    class WithAnotherAggregateId(aggregateId: SampleAggregateId, aggregateVersion: AggregateVersion, occurredAt: Instant, val anotherAggregateId: AnotherSampleAggregateId) : SampleDomainEvent(aggregateId, aggregateVersion, occurredAt)
}




