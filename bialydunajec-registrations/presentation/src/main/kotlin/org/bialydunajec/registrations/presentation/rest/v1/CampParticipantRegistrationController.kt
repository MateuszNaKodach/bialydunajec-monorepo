package org.bialydunajec.registrations.presentation.rest.v1

import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.bialydunajec.registrations.presentation.rest.v1.request.VerifyCampParticipantRegistrationRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/camp-registrations/camp-participant-registration")
internal class CampParticipantRegistrationController(val commandGateway: CampRegistrationsCommandGateway) {


    @PatchMapping("/{campParticipantRegistrationId}/verification")
    fun verifyCampParticipantRegistration(
            @PathVariable campParticipantRegistrationId: String,
            @RequestBody request: VerifyCampParticipantRegistrationRequest) =
            commandGateway.process(CampRegistrationsCommand.VerifyCampParticipantRegistrationCommand(CampParticipantRegistrationId(campParticipantRegistrationId), request.verificationCode))

}