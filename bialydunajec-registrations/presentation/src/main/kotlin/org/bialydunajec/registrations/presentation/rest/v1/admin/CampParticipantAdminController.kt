package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
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
  @GetMapping
  fun getCampParticipantsByCottageId(@RequestParam(required = false) cottageId: String?, pageable: Pageable) =
          when (cottageId) {
              null -> queryGateway.execute(CampParticipantQuery.All(), pageable)
              else -> queryGateway.execute(CampParticipantQuery.ByCottageId(cottageId), pageable)
          }
          */
}