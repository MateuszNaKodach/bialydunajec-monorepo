package org.bialydunajec.registrations.presentation.rest.v1

import org.bialydunajec.ddd.application.base.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.dto.CampRegistrationsCottageDto
import org.bialydunajec.registrations.application.dto.toValueObject
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId
import org.bialydunajec.registrations.domain.shirt.valueobject.CamperShirtOrder
import org.bialydunajec.registrations.presentation.rest.v1.request.CampParticipantRegistrationRequest
import org.springframework.web.bind.annotation.*

//TODO: Is not admin Controller!!
// Camp Participant - bierze udział w konkretnej edycji obozu. Camper - kiedyś brał udział w obozie.
@RestController
@RequestMapping("/rest-api/v1/camp-registrations/in-progress")
class InProgressCampRegistrationsController(
        private val commandGateway: CampRegistrationsCommandGateway,
        private val queryGateway: CampRegistrationsQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PostMapping("/camp-participant") //TODO: Change endpoints for with registrationsId
    fun registerCampParticipant(@RequestBody request: CampParticipantRegistrationRequest) =
            queryGateway.process(CampRegistrationsEditionQuery.InProgress())?.let {
                commandGateway.process(
                        CampRegistrationsCommand.RegisterCampParticipantCommand(
                                CampRegistrationsEditionId(it.campRegistrationsEditionId),
                                with(request) {
                                    CamperApplication(
                                            CottageId(cottageId),
                                            personalData.toValueObject(),
                                            homeAddress.toValueObject(),
                                            EmailAddress(emailAddress),
                                            PhoneNumber(phoneNumber),
                                            camperEducation.toValueObject()
                                    )
                                },
                                with(request) {
                                    CamperShirtOrder(
                                            ShirtColorOptionId(shirtOrder.shirtColorOptionId),
                                            ShirtSizeOptionId(shirtOrder.shirtSizeOptionId)
                                    )
                                }
                        )
                )
            }


    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping
    fun getInProgressCampRegistrationsEdition() =
            queryGateway.process(CampRegistrationsEditionQuery.InProgress())

    @GetMapping("/cottage")
    fun getAllCottagesByInProgressCampRegistrations(@RequestParam camperGender: Gender) =
            queryGateway.process(CampRegistrationsEditionQuery.InProgress())?.let {
                queryGateway.process(CottageQuery.AllActiveByCampRegistrationsEditionId(it.campRegistrationsEditionId, camperGender))
                        .sortedBy { it.name }
            } ?: emptySet<CampRegistrationsCottageDto>()

    @GetMapping("/camp-shirt")
    fun getCampEditionShirtByInProgressCampRegistrations() =
            queryGateway.process(CampRegistrationsEditionQuery.InProgress())?.let {
                queryGateway.process(CampEditionShirtQuery.ByCampRegistrationsEditionId(it.campRegistrationsEditionId))
            }

}