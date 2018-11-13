package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/camp-registrations/camp-participant-registration")
class CampParticipantRegistrationAdminController(
        val commandGateway: CampRegistrationsAdminCommandGateway
) {

    @PatchMapping("/{campParticipantRegistrationId}/verification")
    fun verifyCampParticipantRegistrationByAuthorized(
            @PathVariable campParticipantRegistrationId: String) =
            commandGateway.process(CampRegistrationsCommand.VerifyCampParticipantRegistrationCommandByAuthorized(CampParticipantRegistrationId(campParticipantRegistrationId)))
}