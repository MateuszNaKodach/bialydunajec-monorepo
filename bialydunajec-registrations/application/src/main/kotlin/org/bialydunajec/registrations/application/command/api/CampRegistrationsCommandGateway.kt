package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.registrations.application.command.*
import org.springframework.stereotype.Component

@Component
class CampRegistrationsCommandGateway internal constructor(
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
        private val campParticipantRegistrationApplicationService: CampParticipantRegistrationApplicationService,
        private val campParticipantRegistrationConfirmApplicationService: VerifyCampParticipantRegistrationApplicationService,
        private val updateCampEditionShirtApplicationService: UpdateCampEditionShirtApplicationService,
        private val addCampEditionShirtColorOptionApplicationService: AddCampEditionShirtColorOptionApplicationService,
        private val addCampEditionShirtSizeOptionApplicationService: AddCampEditionShirtSizeOptionApplicationService
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

    fun process(command: CampRegistrationsCommand.RegisterCampParticipantCommand) =
            campParticipantRegistrationApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.VerifyCampParticipantRegistrationCommand) =
            campParticipantRegistrationConfirmApplicationService.process(command)


    fun process(command: CampRegistrationsCommand.UpdateCampEditionShirt) =
            updateCampEditionShirtApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.AddCampEditionShirtColorOption) =
            addCampEditionShirtColorOptionApplicationService.process(command)

    fun process(command: CampRegistrationsCommand.AddCampEditionShirtSizeOption) =
            addCampEditionShirtSizeOptionApplicationService.process(command)
}