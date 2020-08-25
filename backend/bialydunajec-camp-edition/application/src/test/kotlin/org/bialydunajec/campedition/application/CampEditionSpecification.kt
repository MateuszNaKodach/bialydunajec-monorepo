package org.bialydunajec.campedition.application

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.bialydunajec.campedition.application.command.api.CampEditionCommand
import org.bialydunajec.campedition.domain.campedition.CampEdition
import org.bialydunajec.campedition.domain.campedition.CampEditionId
import org.bialydunajec.campedition.domain.campedition.CampEditionRepository
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.base.event.InMemoryDomainEventsRecorder
import org.bialydunajec.ddd.domain.base.persistence.InMemoryDomainRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class CampEditionSpecification {

    private val externalEvents = anExternalEventPublisher()
    private val domainEvents = anDomainEventBus()
    private val repository = InMemoryCampEditionRepository(domainEvents)
    private val configuration = CampEditionConfiguration(repository, externalEvents)
    private val commandGateway = configuration.campEditionCommandGateway()
    private val queryGateway = configuration.campEditionQueryGateway()

    @Test
    fun `Given none camp edition exists | When create camp edition | Then camp edition should be created`() {
        val edition36 = CampEditionCommand.CreateCampEdition.from(
                36,
                LocalDate.now().plusDays(15),
                LocalDate.now().plusDays(30),
                419.0,
                99.0
        )

        commandGateway.process(edition36)

        assertThat(domainEvents.recorded).hasSize(1)
    }

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
