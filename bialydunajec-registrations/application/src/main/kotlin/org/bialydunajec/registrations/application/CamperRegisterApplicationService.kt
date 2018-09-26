package org.bialydunajec.registrations.application

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.registrations.application.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsRepository

internal class CamperRegisterApplicationService(val campRegistrationRepository: CampRegistrationsRepository) : ApplicationService {

    fun process(command: CampRegistrationsCommand) {

    }

}