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
            createCampRegistrationsEditionApplicationService.execute(command)

    internal fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsEditionDuration) =
            updateCampRegistrationsEditionDurationApplicationService.execute(command)


    fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsTimer) =
            setupCampRegistrationsApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.StartCampRegistrationsNow) =
            startCampRegistrationsNowApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.SuspendCampRegistrationsNow) =
            suspendCampRegistrationsNowApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.UnsuspendCampRegistrationsNow) =
            unsuspendCampRegistrationsNowApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.FinishCampRegistrationsNow) =
            finishCampRegistrationsNowApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.CreateAcademicMinistryCottage) =
            createAcademicMinistryCottageApplicationService.execute(command)


    fun process(command: CampRegistrationsCommand.CreateStandaloneCottage) =
            standaloneCottageApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.UpdateCottage) =
            updateCottageApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.ActivateCottage) =
            activateCottageApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.DeactivateCottage) =
            deactivateCottageApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.VerifyCampParticipantRegistrationCommandByAuthorized) =
            authorizedCampParticipantRegistrationConfirmApplicationService.execute(command)


    fun process(command: CampRegistrationsCommand.UpdateCampEditionShirt) =
            updateCampEditionShirtApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.AddCampEditionShirtColorOption) =
            addCampEditionShirtColorOptionApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.UpdateCampEditionShirtColorOption) =
            updateCampEditionShirtColorOptionApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.AddCampEditionShirtSizeOption) =
            addCampEditionShirtSizeOptionApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.UpdateCampEditionShirtSizeOption) =
            updateCampEditionShirtSizeOptionApplicationService.execute(command)
}