package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.registrations.application.command.*
import org.springframework.stereotype.Component

@Component
class CampRegistrationsAdminCommandGateway internal constructor(
        private val createCampRegistrationsEditionApplicationService: CreateCampRegistrationsEditionApplicationService,
        private val updateCampRegistrationsEditionDurationApplicationService: UpdateCampRegistrationsEditionDurationApplicationService,
        private val setupCampRegistrationsApplicationService: SetupCampRegistrationsApplicationService,
        private val startCampRegistrationsNowApplicationService: StartCampRegistrationsNowApplicationService,
        private val finishCampRegistrationsNowApplicationService: FinishCampRegistrationsNowApplicationService,
        private val suspendCampRegistrationsNowApplicationService: SuspendCampRegistrationsNowApplicationService,
        private val unsuspendCampRegistrationsNowApplicationService: UnsuspendCampRegistrationsNowApplicationService,
        private val createAcademicMinistryCottageApplicationService: CreateAcademicMinistryCottageApplicationService,
        private val standaloneCottageApplicationService: CreateStandaloneCottageApplicationService,
        private val updateCottageApplicationService: UpdateCottageApplicationService,
        private val activateCottageApplicationService: ActivateCottageApplicationService,
        private val deactivateCottageApplicationService: DeactivateCottageApplicationService,
        private val authorizedCampParticipantRegistrationConfirmApplicationService: VerifyCampParticipantRegistrationByAuthorizedApplicationService,
        private val updateCampEditionShirtApplicationService: UpdateCampEditionShirtApplicationService,
        private val addCampEditionShirtColorOptionApplicationService: AddCampEditionShirtColorOptionApplicationService,
        private val addCampEditionShirtSizeOptionApplicationService: AddCampEditionShirtSizeOptionApplicationService,
        private val updateCampEditionShirtSizeOptionApplicationService: UpdateCampEditionShirtSizeOptionApplicationService,
        private val updateCampEditionShirtColorOptionApplicationService: UpdateCampEditionShirtColorOptionApplicationService
) : CommandGateway {

    internal fun process(command: CampRegistrationsCommand.CreateCampRegistrationsEdition) =
            createCampRegistrationsEditionApplicationService.process(command)

    internal fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsEditionDuration) =
            updateCampRegistrationsEditionDurationApplicationService.process(command)


    fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsTimer) =
            setupCampRegistrationsApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.StartCampRegistrationsNow) =
            startCampRegistrationsNowApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.SuspendCampRegistrationsNow) =
            suspendCampRegistrationsNowApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.UnsuspendCampRegistrationsNow) =
            unsuspendCampRegistrationsNowApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.FinishCampRegistrationsNow) =
            finishCampRegistrationsNowApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.CreateAcademicMinistryCottage) =
            createAcademicMinistryCottageApplicationService.process(command)


    fun process(command: CampRegistrationsCommand.CreateStandaloneCottage) =
            standaloneCottageApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.UpdateCottage) =
            updateCottageApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.ActivateCottage) =
            activateCottageApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.DeactivateCottage) =
            deactivateCottageApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.VerifyCampParticipantRegistrationCommandByAuthorized) =
            authorizedCampParticipantRegistrationConfirmApplicationService.process(command)


    fun process(command: CampRegistrationsCommand.UpdateCampEditionShirt) =
            updateCampEditionShirtApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.AddCampEditionShirtColorOption) =
            addCampEditionShirtColorOptionApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.UpdateCampEditionShirtColorOption) =
            updateCampEditionShirtColorOptionApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.AddCampEditionShirtSizeOption) =
            addCampEditionShirtSizeOptionApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.UpdateCampEditionShirtSizeOption) =
            updateCampEditionShirtSizeOptionApplicationService.process(command)
}