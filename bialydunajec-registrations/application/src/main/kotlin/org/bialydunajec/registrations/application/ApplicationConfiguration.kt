package org.bialydunajec.registrations.application

import org.bialydunajec.registrations.application.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun campRegistrationsCommandGateway(campRegistrationRepository: CampRegistrationsRepository): CampRegistrationsCommandGateway = CampRegistrationsCommandGateway(
            CamperRegisterApplicationService(campRegistrationRepository)
    )
}