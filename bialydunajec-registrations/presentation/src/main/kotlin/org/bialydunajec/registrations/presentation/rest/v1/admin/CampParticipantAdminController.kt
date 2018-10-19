package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.ddd.application.base.query.api.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.dto.CottageDto
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.application.dto.toValueObject
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.UpdateCottageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.Objects.isNull
import javax.validation.Valid

@RestController
@RequestMapping("/rest-api/v1/camp-registrations/camp-participant")
class CampParticipantAdminController(
        private val commandGateway: CampRegistrationsCommandGateway,
        private val queryGateway: CampRegistrationsQueryGateway
) {

    //TODO: Add filtering by campRegistrationsEditionId
    //COMMAND----------------------------------------------------------------------------------------------------------
    /*
    @GetMapping
    fun getCampParticipantsByCottageId(@RequestParam(required = false) cottageId: String?, pageable: Pageable) =
            when (cottageId) {
                null -> queryGateway.process(CampParticipantQuery.All(), pageable)
                else -> queryGateway.process(CampParticipantQuery.ByCottageId(cottageId), pageable)
            }
            */

    @GetMapping
    fun getCampParticipantsByCampRegistrationsId(@RequestParam(required = false) campRegistrationsEditionId: String?, pageable: Pageable) =
            when (campRegistrationsEditionId) {
                null -> queryGateway.process(CampParticipantQuery.All(), pageable)
                else -> queryGateway.process(CampParticipantQuery.ByCampRegistrationsEditionId(campRegistrationsEditionId), pageable)
            }


    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping("/count")
    fun countCampParticipantsByCottageId(@RequestParam cottageId: String) =
            queryGateway.process(CampParticipantQuery.CountByCottageId(cottageId))
}