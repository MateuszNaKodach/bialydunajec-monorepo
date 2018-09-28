package org.bialydunajec.registrations.application.api

import org.bialydunajec.ddd.application.base.CommandGateway
import org.bialydunajec.registrations.application.CreateAcademicMinistryCottageApplicationService
import org.bialydunajec.registrations.application.CreateStandaloneCottageApplicationService
import org.bialydunajec.registrations.application.SetupCampRegistrationsApplicationService
import org.springframework.stereotype.Component

@Component
class CampRegistrationsCommandGateway internal constructor(
        private val setupCampRegistrationsApplicationService: SetupCampRegistrationsApplicationService,
        private val createAcademicMinistryCottageApplicationService: CreateAcademicMinistryCottageApplicationService,
        private val standaloneCottageApplicationService: CreateStandaloneCottageApplicationService
) : CommandGateway<CampRegistrationsCommand> {

    override fun process(command: CampRegistrationsCommand) {
        when (command) {
            is CampRegistrationsCommand.SetupCampRegistrations -> process(command)
            is CampRegistrationsCommand.CreateAcademicMinistryCottage -> process(command)
            is CampRegistrationsCommand.CreateStandaloneCottage -> process(command)
        }
    }

    private fun process(command: CampRegistrationsCommand.SetupCampRegistrations) {
        setupCampRegistrationsApplicationService.process(command)
    }

    private fun process(command: CampRegistrationsCommand.CreateAcademicMinistryCottage) {
        createAcademicMinistryCottageApplicationService.process(command)
    }

    private fun process(command: CampRegistrationsCommand.CreateStandaloneCottage) {
        standaloneCottageApplicationService.process(command)
    }

}