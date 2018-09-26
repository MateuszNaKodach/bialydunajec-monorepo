package org.bialydunajec.registrations.application.api

import org.bialydunajec.ddd.application.base.CommandGateway
import org.bialydunajec.registrations.application.CamperRegisterApplicationService
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrations
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

class CampRegistrationsCommandGateway internal constructor(
        private val camperRegisterApplicationService: CamperRegisterApplicationService
) : CommandGateway<CampRegistrationsCommand> {

    fun process(command: CampRegistrationsCommand) {
        camperRegisterApplicationService.process(command)
    }

    @PostConstruct
    fun post(){
        this.camperRegisterApplicationService.campRegistrationRepository.save(CampRegistrations(CampRegistrationsId(1)))
    }
}