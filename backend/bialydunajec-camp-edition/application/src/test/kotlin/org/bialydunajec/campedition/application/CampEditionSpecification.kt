package org.bialydunajec.campedition.application

import assertk.Assert
import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.support.expected
import assertk.assertions.support.show
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.bialydunajec.campedition.application.command.api.CampEditionCommand
import org.bialydunajec.campedition.application.command.api.CampEditionCommandGateway
import org.bialydunajec.campedition.application.query.api.CampEditionQuery
import org.bialydunajec.campedition.application.query.api.CampEditionQueryGateway
import org.bialydunajec.campedition.application.query.api.dto.CampEditionDto
import org.bialydunajec.campedition.domain.campedition.CampEdition
import org.bialydunajec.campedition.domain.campedition.CampEditionEvent
import org.bialydunajec.campedition.domain.campedition.CampEditionId
import org.bialydunajec.campedition.domain.campedition.CampEditionRepository
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.base.event.DomainEventsRecorder
import org.bialydunajec.ddd.domain.base.event.InMemoryDomainEventsRecorder
import org.bialydunajec.ddd.domain.base.persistence.InMemoryDomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

internal class CampEditionSpecification {

    private val campEditionGivens = mapOf(
            35 to CampEditionGiven(
                    campEditionNumber = 35,
                    campEditionStartDate = LocalDate.of(2018, 9, 2),
                    campEditionEndDate = LocalDate.of(2018, 9, 16),
                    campEditionPrice = 399.0,
                    campEditionDownPaymentAmount = 99.0
            ),
            36 to CampEditionGiven(
                    campEditionNumber = 36,
                    campEditionStartDate = LocalDate.of(2019, 9, 2),
                    campEditionEndDate = LocalDate.of(2019, 9, 16),
                    campEditionPrice = 419.0,
                    campEditionDownPaymentAmount = 99.0
            )
    )

    @Nested
    @DisplayName("Given no camp editions exist")
    inner class GivenNoCampEditionsExists {

        val campEdition35 = campEditionGivens.getValue(35)
        val campEdition36 = campEditionGivens.getValue(36)
        val scenario = campEditions()

        @Nested
        @DisplayName("When create first camp edition")
        inner class WhenCreateFirstCampEdition {

            @BeforeEach
            internal fun setUp() {
                scenario { whenExecute(campEdition35.create) }
            }

            @Test
            fun `Then camp edition should be created`() {
                scenario { publishedLastly(campEdition35.created) }
            }

            @Test
            fun `Then camp edition should be searchable by id`() {
                scenario { publishedLastly(campEdition35.created) }
            }

            @Test
            fun `Then camp edition should be searchable with all camp editions`() {
                scenario { resultOf({ process(CampEditionQuery.All) }) { containsOnly(campEdition35.dto) } }
            }


        }


    }

    @Test
    fun `Given none camp edition exists | When create camp edition | Then camp edition should be created`() {
        val campEdition35 = campEditionGivens.getValue(35)

        campEditions {
            whenExecute(campEdition35.create) thenExpect (campEdition35.created)

            resultOf({ process(CampEditionQuery.All) }) { containsOnly(campEdition35.dto) }
            resultOf({ process(CampEditionQuery.ById(35)) }) { isEqualTo(campEdition35.dto) }
        }

    }

    @Test
    fun `Given camp edition exists | When create camp edition, which dates do not overlap with the first one | Then second camp edition should be created`() {
        val campEdition35 = campEditionGivens.getValue(35)
        val campEdition36 = campEditionGivens.getValue(36)

        campEditions {
            givenCampEditionExists(campEdition35)

            whenExecute(campEdition36.create) thenExpect (campEdition36.created)

            resultOf({ process(CampEditionQuery.All) }) { containsOnly(campEdition35.dto, campEdition36.dto) }
            resultOf({ process(CampEditionQuery.ById(35)) }) { isEqualTo(campEdition35.dto) }
            resultOf({ process(CampEditionQuery.ById(36)) }) { isEqualTo(campEdition36.dto) }
        }

    }

}

class CampEditionGiven(
        val campEditionNumber: Int,
        val campEditionStartDate: LocalDate,
        val campEditionEndDate: LocalDate,
        val campEditionPrice: Double,
        val campEditionDownPaymentAmount: Double
) {

    val create = CampEditionCommand.CreateCampEdition.from(
            campEditionNumber = campEditionNumber,
            campEditionStartDate = campEditionStartDate,
            campEditionEndDate = campEditionEndDate,
            campEditionPrice = campEditionPrice,
            campEditionDownPaymentAmount = campEditionDownPaymentAmount
    )
    val created = CampEditionEvent.CampEditionCreated(
            campEditionId = CampEditionId(campEditionNumber),
            startDate = campEditionStartDate,
            endDate = campEditionEndDate,
            totalPrice = Money(campEditionPrice),
            downPaymentAmount = Money(campEditionDownPaymentAmount)
    )
    val dto = CampEditionDto(
            campEditionId = campEditionNumber.toString(),
            campEditionStartDate = campEditionStartDate,
            campEditionEndDate = campEditionEndDate,
            campEditionYear = campEditionStartDate.year,
            campEditionPrice = campEditionPrice,
            campEditionDownPaymentAmount = campEditionDownPaymentAmount
    )

}

class CampEditionTestFixtureScope(
        val commandGateway: CampEditionCommandGateway,
        val queryGateway: CampEditionQueryGateway,
        val domainEvents: InMemoryDomainEventsRecorder,
) {

    operator fun invoke(block: (CampEditionTestFixtureScope.() -> Unit)): CampEditionTestFixtureScope = apply { block(this) }

    infix fun givenCampEditionExists(given: CampEditionGiven): CampEditionTestFixtureScope = apply {
        commandGateway.process(given.create)
    }

    fun givenCampEditionsExists(vararg givens: CampEditionGiven): CampEditionTestFixtureScope = apply {
        givens.map { commandGateway.process(it.create) }
    }

    infix fun whenExecute(command: () -> CampEditionCommand): CampEditionTestFixtureExpect {
        commandGateway.process(command())
        return CampEditionTestFixtureExpect(queryGateway, domainEvents)
    }

    infix fun whenExecute(command: CampEditionCommand): CampEditionTestFixtureExpect {
        commandGateway.process(command)
        return CampEditionTestFixtureExpect(queryGateway, domainEvents)
    }

    fun whenCommands(vararg commands: CampEditionCommand): CampEditionTestFixtureExpect {
        commands.map { commandGateway.process(it) }
        return CampEditionTestFixtureExpect(queryGateway, domainEvents)
    }

    infix fun whenCommands(command: CampEditionCommandGateway.() -> Any): CampEditionTestFixtureExpect {
        command(commandGateway)
        return CampEditionTestFixtureExpect(queryGateway, domainEvents)
    }

    inline infix fun <reified T : CampEditionEvent> publishedLastly(event: T): CampEditionTestFixtureScope = apply {
        assertThat(domainEvents).publishedLastly<T>().equalsToDomainEvent(event)
    }

    inline fun <reified R> resultOf(query: CampEditionQueryGateway.() -> R, assertions: Assert<R>.() -> Unit): CampEditionTestFixtureScope = apply {
        assertions(assertThat(query(queryGateway)))
    }
}

object NoEvents
typealias noEvents = NoEvents

class CampEditionTestFixtureExpect(
        val queryGateway: CampEditionQueryGateway,
        val domainEvents: InMemoryDomainEventsRecorder,
) {

    inline infix fun <reified T : CampEditionEvent> thenExpect(event: () -> T): CampEditionTestFixtureExpect = apply {
        assertThat(domainEvents).publishedOnly<T>().equalsToDomainEvent(event())
    }

    inline infix fun <reified T : CampEditionEvent> thenExpect(event: T): CampEditionTestFixtureExpect = apply {
        assertThat(domainEvents).publishedLastly<T>().equalsToDomainEvent(event)
    }

    infix fun thenExpect(noEvents: NoEvents): CampEditionTestFixtureExpect = apply {
        assertThat(domainEvents).publishedNone()
    }

    inline infix fun <reified R> andResultOf(query: CampEditionQuery): Assert<R> = assertThat(queryGateway.process(query) as R)

    inline infix fun <reified R> andResultOf(query: (queryGateway: CampEditionQueryGateway) -> R): Assert<R> = assertThat(query(queryGateway))

    inline fun <reified R> andResultOf(query: CampEditionQuery, assertions: Assert<R>.() -> Unit): CampEditionTestFixtureExpect {
        return andResultOf({ process(query) as R }, assertions)
    }

    inline fun <reified R> andResultOf(query: CampEditionQueryGateway.() -> R, assertions: Assert<R>.() -> Unit): CampEditionTestFixtureExpect = apply {
        assertions(assertThat(query(queryGateway)))
    }

}


private fun campEditions(block: (CampEditionTestFixtureScope.() -> Unit)? = null): CampEditionTestFixtureScope {
    val externalEvents = anExternalEventPublisher()
    val domainEvents = anDomainEventBus()
    val repository = InMemoryCampEditionRepository(domainEvents)
    val configuration = CampEditionConfiguration(repository, externalEvents)
    val commandGateway = configuration.campEditionCommandGateway()
    val queryGateway = configuration.campEditionQueryGateway()
    val fixture = CampEditionTestFixtureScope(commandGateway, queryGateway, domainEvents)
    block?.invoke(fixture)
    return fixture
}


private fun anExternalEventPublisher(): ExternalEventPublisher {
    return mockk {
        every { send(any()) } just Runs
        every { sendAll(any()) } just Runs
    }
}

private fun anDomainEventBus(): InMemoryDomainEventsRecorder {
    val domainEventBus: DomainEventBus = mockk {
        every { publish(any()) } just Runs
        every { publishAll(any()) } just Runs
    }
    return InMemoryDomainEventsRecorder(domainEventBus)
}

class InMemoryCampEditionRepository(domainEventBus: DomainEventBus)
    : InMemoryDomainRepository<CampEditionId, CampEdition>(domainEventBus = domainEventBus), CampEditionRepository


inline fun <reified EventType : DomainEvent<*>> Assert<DomainEventsRecorder>.publishedLastly(): Assert<EventType> =
        published(eventRetriever = { it.last() })

inline fun <reified EventType : DomainEvent<*>> Assert<DomainEventsRecorder>.published(index: Int): Assert<EventType> =
        published(eventRetriever = { it.getOrNull(index) })

inline fun <reified EventType : DomainEvent<*>> Assert<DomainEventsRecorder>.published(crossinline eventRetriever: (domainEvents: List<DomainEvent<*>>) -> DomainEvent<*>?): Assert<EventType> = transform { actual ->
    if (actual.recorded.isEmpty()) {
        expected("published event but nothing was published")
    } else {
        val lastPublished = eventRetriever(actual.recorded)
                ?: expected("published event of type ${show(EventType::class.simpleName)} but was ${show("null")}")
        if (lastPublished is EventType) {
            return@transform lastPublished
        } else {
            expected("published event of type ${show(EventType::class.simpleName)} but was ${show(lastPublished::class.simpleName)}")
        }
    }
}

inline fun <reified EventType : DomainEvent<*>> Assert<DomainEventsRecorder>.publishedOnly(): Assert<EventType> = transform { actual ->
    when (actual.recorded.size) {
        in 0..0 -> expected("only one event but no events occurred")
        in 1..1 -> {
            val published = actual.recorded.first()
            if (published is EventType) {
                return@transform published
            } else {
                expected("published event of type ${show(EventType::class.simpleName)} but was ${show(actual.recorded.first()::class.simpleName)}")
            }
        }
        in 2..actual.recorded.size -> expected("only event: ${show(actual.recorded.first())} but occurred events: ${show(actual.recorded.contentToString())}")
        else -> return@transform actual.recorded.first() as EventType
    }
}

fun Assert<DomainEventsRecorder>.publishedNone() = given { actual ->
    if (actual.recorded.isNotEmpty()) {
        expected("none event but occurred events: ${show(actual.recorded.contentToString())}")
    } else {
        return
    }
}

inline fun <reified EventType : DomainEvent<*>> Assert<EventType>.equalsToDomainEvent(event: EventType): Assert<EventType> = transform { actual ->
    if (equalsByPropertiesIgnoresDomainEventIdAndOccurredAt(actual, event).not()) {
        expected("event: ${show(event)} but occurred: ${show(actual)}")
    } else {
        return@transform actual
    }
}

fun List<Any>.contentToString() = this.joinToString(
        separator = ", ",
        prefix = "[",
        postfix = "]"
)

fun equalsByPropertiesIgnoresDomainEventIdAndOccurredAt(e1: Any, e2: Any) =
        equalsByProperties(e1, e2, setOf("domainEventId", "occurredAt"))

fun equalsByProperties(e1: Any, e2: Any, ignoredProperties: Collection<String> = setOf()) =
        e1::class.memberProperties
                .map { it.name }
                .filter { ignoredProperties.isEmpty() || !ignoredProperties.contains(it) }
                .all { readInstanceProperty(e1, it) == readInstanceProperty(e2, it) }

@Suppress("UNCHECKED_CAST")
fun readInstanceProperty(instance: Any, propertyName: String): Any? {
    val property = instance::class.memberProperties
            .first { it.name == propertyName } as KProperty1<Any, *>
    return property.get(instance)
}
