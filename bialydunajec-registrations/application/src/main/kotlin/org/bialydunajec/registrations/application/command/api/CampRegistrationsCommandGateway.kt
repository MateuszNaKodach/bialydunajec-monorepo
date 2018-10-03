package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.registrations.application.command.*
import org.springframework.stereotype.Component

@Component
class CampRegistrationsCommandGateway internal constructor(
        private val createCampRegistrationsApplicationService: CreateCampRegistrationsApplicationService,
        private val setupCampRegistrationsApplicationService: SetupCampRegistrationsApplicationService,
        private val startCampRegistrationsNowApplicationService: StartCampRegistrationsNowApplicationService,
        private val finishCampRegistrationsNowApplicationService: FinishCampRegistrationsNowApplicationService,
        private val suspendCampRegistrationsNowApplicationService: SuspendCampRegistrationsNowApplicationService,
        private val unsuspendCampRegistrationsNowApplicationService: UnsuspendCampRegistrationsNowApplicationService,
        private val createAcademicMinistryCottageApplicationService: CreateAcademicMinistryCottageApplicationService,
        private val standaloneCottageApplicationService: CreateStandaloneCottageApplicationService
) : CommandGateway {

    fun process(command: CampRegistrationsCommand.CreateCampRegistrations) {
        createCampRegistrationsApplicationService.process(command)
    }

    fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsTimer) {
        setupCampRegistrationsApplicationService.process(command)
    }

    fun process(command: CampRegistrationsCommand.StartCampRegistrationsNow) {
        startCampRegistrationsNowApplicationService.process(command)
    }

    fun process(command: CampRegistrationsCommand.SuspendCampRegistrationsNow) {
        suspendCampRegistrationsNowApplicationService.process(command)
    }

    fun process(command: CampRegistrationsCommand.UnsuspendCampRegistrationsNow) {
        unsuspendCampRegistrationsNowApplicationService.process(command)
    }

    fun process(command: CampRegistrationsCommand.FinishCampRegistrationsNow) {
        finishCampRegistrationsNowApplicationService.process(command)
    }

    fun process(command: CampRegistrationsCommand.CreateAcademicMinistryCottage) {
        createAcademicMinistryCottageApplicationService.process(command)
    }

    fun process(command: CampRegistrationsCommand.CreateStandaloneCottage) {
        standaloneCottageApplicationService.process(command)
    }


}