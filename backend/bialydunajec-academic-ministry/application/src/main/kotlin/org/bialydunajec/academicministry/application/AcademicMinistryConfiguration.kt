package org.bialydunajec.academicministry.application

import org.bialydunajec.academicministry.application.command.*
import org.bialydunajec.academicministry.application.command.api.AcademicMinistryAdminCommandGateway
import org.bialydunajec.academicministry.application.eventlistener.AcademicMinistryDomainEventsPropagator
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryAdminQueryGateway
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQueryGateway
import org.bialydunajec.academicministry.application.query.readmodel.AcademicMinistryDomainModelReader
import org.bialydunajec.academicministry.domain.AcademicMinistryRepository
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class AcademicMinistryConfiguration(private val academicMinistryRepository: AcademicMinistryRepository,
                                             private val externalEventPublisher: ExternalEventPublisher) {

    @Bean
    fun academicMinistryAdminCommandGateway(): AcademicMinistryAdminCommandGateway {
        val createAcademicMinistryApplicationService = CreateAcademicMinistryApplicationService(academicMinistryRepository)
        val updateAcademicMinistryApplicationService = UpdateAcademicMinistryApplicationService(academicMinistryRepository)
        val createAcademicMinistryPriestApplicationService = CreateAcademicMinistryPriestApplicationService(academicMinistryRepository)
        val removeAcademicMinistryPriestApplicationService = RemoveAcademicMinistryPriestApplicationService(academicMinistryRepository)
        return AcademicMinistryAdminCommandGateway(
                createAcademicMinistryApplicationService,
                updateAcademicMinistryApplicationService,
                createAcademicMinistryPriestApplicationService,
                removeAcademicMinistryPriestApplicationService
        )
    }

    @Bean
    fun academicMinistryAdminQueryGateway(): AcademicMinistryAdminQueryGateway {
        val academicMinistryDomainModelReader = AcademicMinistryDomainModelReader(academicMinistryRepository)
        return AcademicMinistryAdminQueryGateway(academicMinistryDomainModelReader)
    }

    @Bean
    fun academicMinistryQueryGateway(): AcademicMinistryQueryGateway {
        val academicMinistryDomainModelReader = AcademicMinistryDomainModelReader(academicMinistryRepository)
        return AcademicMinistryQueryGateway(academicMinistryDomainModelReader)
    }

    @Bean
    fun academicMinistryDomainEventsPropagator(): AcademicMinistryDomainEventsPropagator {
        return AcademicMinistryDomainEventsPropagator(externalEventPublisher)
    }

}
