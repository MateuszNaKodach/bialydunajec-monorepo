package org.bialydunajec.campedition.application

import org.bialydunajec.campedition.application.command.CreateCampEditionApplicationService
import org.bialydunajec.campedition.application.command.UpdateCampEditionDurationApplicationService
import org.bialydunajec.campedition.application.command.api.CampEditionCommandGateway
import org.bialydunajec.campedition.application.eventlistener.CampEditionDomainEventsPropagator
import org.bialydunajec.campedition.application.query.api.CampEditionQueryGateway
import org.bialydunajec.campedition.application.query.readmodel.CampEditionDomainModelReader
import org.bialydunajec.campedition.domain.campedition.CampEditionRepository
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class CampEditionConfiguration(private val campEditionRepository: CampEditionRepository,
                                        private val externalEventPublisher: ExternalEventPublisher) {

    @Bean
    fun campEditionCommandGateway(): CampEditionCommandGateway {
        return CampEditionCommandGateway(
                CreateCampEditionApplicationService(campEditionRepository),
                UpdateCampEditionDurationApplicationService(campEditionRepository)
        )
    }

    @Bean
    fun campEditionQueryGateway(): CampEditionQueryGateway {
        return CampEditionQueryGateway(CampEditionDomainModelReader(campEditionRepository))
    }

    @Bean
    fun campEditionDomainEventsPropagator(): CampEditionDomainEventsPropagator {
        return CampEditionDomainEventsPropagator(externalEventPublisher)
    }
}
