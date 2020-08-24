package org.bialydunajec.campedition.application

import org.bialydunajec.campedition.application.command.CreateCampEditionApplicationService
import org.bialydunajec.campedition.application.command.UpdateCampEditionDurationApplicationService
import org.bialydunajec.campedition.application.command.api.CampEditionCommandGateway
import org.bialydunajec.campedition.application.query.api.CampEditionQueryGateway
import org.bialydunajec.campedition.application.query.readmodel.CampEditionDomainModelReader
import org.bialydunajec.campedition.domain.campedition.CampEditionRepository
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandBus
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.springframework.context.annotation.Configuration

@Configuration
internal class CampEditionConfiguration {

    fun campEditionModule(
            domainEventBus: DomainEventBus,
            campEditionRepository: CampEditionRepository,
            externalCommandBus: ExternalCommandBus,
            externalEventPublisher: ExternalEventPublisher
    ): CampEditionModule {
        val campEditionCommandGateway = CampEditionCommandGateway(
                CreateCampEditionApplicationService(campEditionRepository),
                UpdateCampEditionDurationApplicationService(campEditionRepository)
        )
        val campEditionQueryGateway = CampEditionQueryGateway(CampEditionDomainModelReader(campEditionRepository))
        return CampEditionModule(campEditionCommandGateway, campEditionQueryGateway, domainEventBus, externalCommandBus, externalEventPublisher)
    }
}
