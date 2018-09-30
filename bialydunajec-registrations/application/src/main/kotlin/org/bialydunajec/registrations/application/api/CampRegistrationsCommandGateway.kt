package org.bialydunajec.registrations.application.api

import org.bialydunajec.ddd.application.base.CommandGateway
import org.bialydunajec.registrations.application.*
import org.springframework.stereotype.Component

@Component
class CampRegistrationsCommandGateway internal constructor(
        private val setupCampRegistrationsApplicationService: SetupCampRegistrationsApplicationService,
        private val startCampRegistrationsNowApplicationService: StartCampRegistrationsNowApplicationService,
        private val finishCampRegistrationsNowApplicationService: FinishCampRegistrationsNowApplicationService,
        private val createAcademicMinistryCottageApplicationService: CreateAcademicMinistryCottageApplicationService,
        private val standaloneCottageApplicationService: CreateStandaloneCottageApplicationService
) : CommandGateway<CampRegistrationsCommand> {

    override fun process(command: CampRegistrationsCommand) {
        when (command) {
            is CampRegistrationsCommand.UpdateCampRegistrationsTimer -> process(command)
            is CampRegistrationsCommand.StartCampRegistrationsNow -> process(command)
            is CampRegistrationsCommand.FinishCampRegistrationsNow -> process(command)
            is CampRegistrationsCommand.CreateAcademicMinistryCottage -> process(command)
            is CampRegistrationsCommand.CreateStandaloneCottage -> process(command)
        }
    }

    private fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsTimer) {
        setupCampRegistrationsApplicationService.process(command)
    }

    private fun process(command: CampRegistrationsCommand.StartCampRegistrationsNow) {
        startCampRegistrationsNowApplicationService.process(command)
    }

    private fun process(command: CampRegistrationsCommand.FinishCampRegistrationsNow) {
        finishCampRegistrationsNowApplicationService.process(command)
    }

    private fun process(command: CampRegistrationsCommand.CreateAcademicMinistryCottage) {
        createAcademicMinistryCottageApplicationService.process(command)
    }

    private fun process(command: CampRegistrationsCommand.CreateStandaloneCottage) {
        standaloneCottageApplicationService.process(command)
    }

}