package org.bialydunajec.registrations.presentation.rest.v1

import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.presentation.rest.v1.request.UpdateCampRegistrationsTimerRequest
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/rest-api/v1/camp-registrations")
class CampRegistrationsCottageController(
        private val commandGateway: CampRegistrationsCommandGateway,
        private val queryGateway: CampRegistrationsQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PostMapping("/{campRegistrationsEditionId}/cottage/academic-ministry-cottage")
    fun createAcademicMinistryCottage(@PathVariable campRegistrationsEditionId: Int, @RequestParam academicMinistryId: String) =
            commandGateway.process(CampRegistrationsCommand.CreateAcademicMinistryCottage(campRegistrationsEditionId, academicMinistryId))

    @PostMapping("/{campRegistrationsEditionId}/cottage/standalone-cottage")
    fun createStandaloneCottage(@PathVariable campRegistrationsEditionId: Int, @RequestParam cottageName: String) =
            commandGateway.process(CampRegistrationsCommand.CreateStandaloneCottage(campRegistrationsEditionId, cottageName))


    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping("/{campRegistrationsEditionId}/cottage")
    fun getAllCottagesByCampRegistrationsEditionId(@PathVariable campRegistrationsEditionId: String) =
            queryGateway.process(CottageQuery.AllByCampRegistrationsEditionId(campRegistrationsEditionId))
                    .sortedByDescending { it.name }

    @GetMapping("/{campRegistrationsEditionId}/cottage/{cottageId}")
    fun getByIdAndCampRegistrationsEditionId(@PathVariable campRegistrationsEditionId: String, @PathVariable cottageId: String) =
            queryGateway.process(CottageQuery.ByIdAndByCampRegistrationsEditionId(cottageId, campRegistrationsEditionId))

}