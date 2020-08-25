package org.bialydunajec.campedition.application

import assertk.Assert
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
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
import org.bialydunajec.campedition.domain.exception.CampEditionDomainRule
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.base.event.DomainEventsRecorder
import org.bialydunajec.ddd.domain.base.event.InMemoryDomainEventsRecorder
import org.bialydunajec.ddd.domain.base.persistence.InMemoryDomainRepository
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
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


    @Test
    fun `Given none camp edition exists | When create camp edition | Then camp edition should be created`() {
        val campEdition35 = campEditionGivens.getValue(35)

        campEditions {
            whenExecute(campEdition35.create) thenExpect (campEdition35.created)

            thenResultOf { process(CampEditionQuery.All) }.containsOnly(campEdition35.dto)
            thenResultOf { process(CampEditionQuery.ById(35)) }.isEqualTo(campEdition35.dto)
        }

    }

    @Test
    fun `Given camp edition exists | When create camp edition, which dates do not overlap with the first one | Then second camp edition should be created`() {
        val campEdition35 = campEditionGivens.getValue(35)
        val campEdition36 = campEditionGivens.getValue(36)

        campEditions {
            givenCampEditionExists(campEdition35)

            whenExecute(campEdition36.create) thenExpect (campEdition36.created)

            thenResultOf { process(CampEditionQuery.All) }.containsOnly(campEdition35.dto, campEdition36.dto)
            thenResultOf { process(CampEditionQuery.ById(35)) }.isEqualTo(campEdition35.dto)
            thenResultOf { process(CampEditionQuery.ById(36)) }.isEqualTo(campEdition36.dto)
        }

    }

    @Test
    fun `Given camp edition exists | When update the camp edition duration | Then the camp edition duration should be updated`() {
        val campEdition35 = campEditionGivens.getValue(35)
        val newStartDate = LocalDate.of(2017, 9, 5)
        val newEndDate = LocalDate.of(2017, 9, 10)

        campEditions {
            givenCampEditionExists(campEdition35)

            whenExecute(campEdition35.updateDuration(newStartDate, newEndDate))

            thenPublishedLastly(campEdition35.updated(newStartDate, newEndDate))
            val updated35campEdition = campEdition35.updatedDto(newStartDate, newEndDate)
            thenResultOf { process(CampEditionQuery.All) }.containsOnly(updated35campEdition)
            thenResultOf { process(CampEditionQuery.ById(35)) }.isEqualTo(updated35campEdition)
        }

    }

    @Test
    fun `Given camp edition doesn't exists | When try to update not existing camp edition | Then update should fail`() {
        val campEdition35 = campEditionGivens.getValue(35)
        val newStartDate = LocalDate.of(2017, 9, 5)
        val newEndDate = LocalDate.of(2017, 9, 10)

        campEditions {
            whenExecute(campEdition35.updateDuration(newStartDate, newEndDate))

            thenRuleBroken(CampEditionDomainRule.CAMP_EDITION_TO_UPDATE_MUST_EXISTS)
            thenNothingPublished()
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

    fun updateDuration(start: LocalDate, end: LocalDate): CampEditionCommand.UpdateCampEditionDuration =
            CampEditionCommand.UpdateCampEditionDuration(CampEditionId(campEditionNumber), start, end)

    fun updated(start: LocalDate, end: LocalDate): CampEditionEvent.CampEditionDurationUpdated =
            CampEditionEvent.CampEditionDurationUpdated(CampEditionId(campEditionNumber), start, end)

    fun updatedDto(start: LocalDate, end: LocalDate): CampEditionDto =
            CampEditionDto(
                    campEditionId = campEditionNumber.toString(),
                    campEditionStartDate = start,
                    campEditionEndDate = end,
                    campEditionYear = start.year,
                    campEditionPrice = campEditionPrice,
                    campEditionDownPaymentAmount = campEditionDownPaymentAmount
            )
}

class CampEditionTestFixtureScope(
        val commandGateway: CampEditionCommandGateway,
        val queryGateway: CampEditionQueryGateway,
        val domainEvents: InMemoryDomainEventsRecorder,
) {

    private val brokenRules = mutableListOf<DomainRuleViolationException>()

    operator fun invoke(block: (CampEditionTestFixtureScope.() -> Unit)): CampEditionTestFixtureScope = apply { block(this) }

    infix fun givenCampEditionExists(given: CampEditionGiven): CampEditionTestFixtureScope = apply {
        commandGateway.process(given.create)
    }

    private fun <R : Any> collectDomainException(block: () -> R): R? {
        try {
            return block()
        } catch (domainException: DomainRuleViolationException) {
            brokenRules.add(domainException)
        }
        return null
    }

    fun givenCampEditionsExists(vararg givens: CampEditionGiven): CampEditionTestFixtureScope = apply {
        givens.map { commandGateway.process(it.create) }
    }

    infix fun whenExecute(command: () -> CampEditionCommand): CampEditionTestFixtureExpect {
        collectDomainException { commandGateway.process(command()) }
        return CampEditionTestFixtureExpect(queryGateway, domainEvents)
    }

    infix fun whenExecute(command: CampEditionCommand): CampEditionTestFixtureExpect {
        collectDomainException { commandGateway.process(command) }
        return CampEditionTestFixtureExpect(queryGateway, domainEvents)
    }

    fun whenCommands(vararg commands: CampEditionCommand): CampEditionTestFixtureExpect {
        commands.map { collectDomainException { commandGateway.process(it) } }
        return CampEditionTestFixtureExpect(queryGateway, domainEvents)
    }

    infix fun whenCommands(command: CampEditionCommandGateway.() -> Any): CampEditionTestFixtureExpect {
        collectDomainException { command(commandGateway) }
        return CampEditionTestFixtureExpect(queryGateway, domainEvents)
    }

    inline infix fun <reified T : CampEditionEvent> thenPublishedLastly(event: T): CampEditionTestFixtureScope = apply {
        assertThat(domainEvents).publishedLastly<T>().equalsToDomainEvent(event)
    }

    fun thenNothingPublished(): CampEditionTestFixtureScope = apply {
        assertThat(domainEvents).publishedNone()
    }

    inline infix fun <reified R> thenResultOf(query: CampEditionQueryGateway.() -> R) = assertThat(query(queryGateway))

    infix fun thenRuleBroken(domainRule: DomainRule)  = apply {
        assertThat(brokenRules).isNotEmpty()
        assertThat(brokenRules.last().violatedRules).contains(domainRule)
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
