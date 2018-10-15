package org.bialydunajec.registrations.presentation.rest.v1

import org.bialydunajec.ddd.application.base.query.api.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.dto.CottageDto
import org.bialydunajec.registrations.application.dto.toValueObject
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.presentation.rest.v1.request.CampParticipantRegistrationRequest
import org.springframework.web.bind.annotation.*

//TODO: Is not admin Controller!!

@RestController
@RequestMapping("/rest-api/v1/camp-registrations/in-progress")
class InProgressCampRegistrationsController(
        private val commandGateway: CampRegistrationsCommandGateway,
        private val queryGateway: CampRegistrationsQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PostMapping("/camp-participant")
    fun registerCampParticipant(@PathVariable campRegistrationsEditionId: Int, @RequestBody request: CampParticipantRegistrationRequest) =
            commandGateway.process(
                    CampRegistrationsCommand.CampParticipantRegistrationCommand(
                            CampRegistrationsEditionId(campRegistrationsEditionId),
                            with(request) {
                                CamperApplication(
                                        CottageId(cottageId),
                                        personalData.toValueObject(),
                                        homeAddress.toValueObject(),
                                        EmailAddress(emailAddress),
                                        PhoneNumber(phoneNumber),
                                        camperEducation.toValueObject()
                                )
                            }
                    )
            )


    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping
    fun getInProgressCampRegistrationsEdition() =
            queryGateway.process(CampRegistrationsEditionQuery.InProgress())

    @GetMapping("/cottage")
    fun getAllCottagesByInProgressCampRegistrations() =
            queryGateway.process(CampRegistrationsEditionQuery.InProgress())?.let {
                queryGateway.process(CottageQuery.AllActiveByCampRegistrationsEditionId(it.campRegistrationsEditionId))
                        .sortedByDescending { it.name }
            } ?: emptySet<CottageDto>()

}