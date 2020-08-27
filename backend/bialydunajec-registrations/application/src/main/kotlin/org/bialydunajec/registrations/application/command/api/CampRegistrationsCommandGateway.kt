package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.registrations.application.command.*
import org.springframework.transaction.annotation.Transactional

@Transactional
class CampRegistrationsCommandGateway internal constructor(
        private val campParticipantRegistrationApplicationService: CampParticipantRegistrationApplicationService,
        private val campParticipantRegistrationConfirmApplicationService: VerifyCampParticipantRegistrationApplicationService
) : CommandGateway {

    fun process(command: CampRegistrationsCommand.RegisterCampParticipantCommand, asynchronous: Boolean = true) =
            if (asynchronous)
                campParticipantRegistrationApplicationService.execute(command)
            else
                campParticipantRegistrationApplicationService.processCommand(command)

    fun process(command: CampRegistrationsCommand.VerifyCampParticipantRegistrationCommand) =
            campParticipantRegistrationConfirmApplicationService.execute(command)

}
