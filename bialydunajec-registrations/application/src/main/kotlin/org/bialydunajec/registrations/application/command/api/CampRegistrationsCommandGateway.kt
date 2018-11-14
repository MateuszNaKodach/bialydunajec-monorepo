package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.registrations.application.command.*
import org.springframework.stereotype.Component

@Component
class CampRegistrationsCommandGateway internal constructor(
        private val campParticipantRegistrationApplicationService: CampParticipantRegistrationApplicationService,
        private val campParticipantRegistrationConfirmApplicationService: VerifyCampParticipantRegistrationApplicationService
) : CommandGateway {

    fun process(command: CampRegistrationsCommand.RegisterCampParticipantCommand) =
            campParticipantRegistrationApplicationService.execute(command)

    fun process(command: CampRegistrationsCommand.VerifyCampParticipantRegistrationCommand) =
            campParticipantRegistrationConfirmApplicationService.execute(command)

}