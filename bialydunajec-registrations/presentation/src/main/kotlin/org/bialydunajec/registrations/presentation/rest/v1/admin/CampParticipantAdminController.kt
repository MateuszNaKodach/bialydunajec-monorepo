package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.ddd.application.base.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.dto.toValueObject
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId
import org.bialydunajec.registrations.domain.shirt.valueobject.CamperShirtOrder
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.CorrectCampParticipantCamperDataRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/camp-registrations/camp-participant")
class CampParticipantAdminController(
        private val commandGateway: CampRegistrationsAdminCommandGateway,
        private val queryGateway: CampRegistrationsQueryGateway
) {

    //TODO: Add filtering by campRegistrationsEditionId
    //COMMAND----------------------------------------------------------------------------------------------------------
    @PatchMapping("/{campParticipantId}")
    fun correctCampParticipantCamperCurrentDataById(@PathVariable campParticipantId: String, @RequestBody requestBody: CorrectCampParticipantCamperDataRequest) =
            commandGateway.process(
                    CampRegistrationsCommand.CorrectCampParticipantRegistrationDataCommand(
                            CampParticipantId(campParticipantId),
                            with(requestBody) {
                                CamperApplication(
                                        CottageId(cottageId),
                                        personalData.toValueObject(),
                                        homeAddress.toValueObject(),
                                        EmailAddress(emailAddress),
                                        PhoneNumber(phoneNumber),
                                        camperEducation.toValueObject()
                                )
                            },
                            with(requestBody) {
                                CamperShirtOrder(
                                        ShirtColorOptionId(shirtOrder.shirtColorOptionId),
                                        ShirtSizeOptionId(shirtOrder.shirtSizeOptionId)
                                )
                            }
                    )
            )

    @DeleteMapping("/{campParticipantId}")
    fun cancelParticipation(@PathVariable campParticipantId: String) =
            commandGateway.process(CampRegistrationsCommand.UnregisterCampParticipantByAuthorizedCommand(CampParticipantId(campParticipantId)))

    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping
    fun getCampParticipantsByCampRegistrationsId(@RequestParam(required = false) campRegistrationsEditionId: String?, pageable: Pageable) =
            when (campRegistrationsEditionId) {
                null -> queryGateway.process(CampParticipantQuery.All(), pageable)
                else -> queryGateway.process(CampParticipantQuery.ByCampRegistrationsEditionId(campRegistrationsEditionId), pageable)
            }

    @GetMapping("/count")
    fun countCampParticipantsByCottageId(@RequestParam cottageId: String) =
            queryGateway.process(CampParticipantQuery.CountByCottageId(cottageId))

    /*
    @GetMapping("/{campParticipantId}")
    fun getCampParticipantById(@PathVariable campParticipantId: String) =
            queryGateway.process(CampParticipantQuery.ById(campParticipantId))

  @GetMapping
  fun getCampParticipantsByCottageId(@RequestParam(required = false) cottageId: String?, pageable: Pageable) =
          when (cottageId) {
              null -> queryGateway.execute(CampParticipantQuery.All(), pageable)
              else -> queryGateway.execute(CampParticipantQuery.ByCottageId(cottageId), pageable)
          }
          */
}