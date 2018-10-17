package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.query.api.AcademicMinistryQuery
import org.bialydunajec.registrations.application.query.api.CampEditionQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsEditionQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsQueryGateway
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.UpdateCampRegistrationsTimerRequest
import org.springframework.web.bind.annotation.*

//TODO: Change to /admin-rest-api/v1
@RestController
@RequestMapping("/rest-api/v1/camp-registrations")
class AcademicMinistryAdminController(
        private val commandGateway: CampRegistrationsCommandGateway,
        private val queryGateway: CampRegistrationsQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------


    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping("/academic-ministry")
    fun getAllAcademicMinistries() =
            queryGateway.process(AcademicMinistryQuery.All())
                    .sortedByDescending { it.displayName }

    @GetMapping("/academic-ministry/{academicMinistryId}")
    fun getAcademicMinistryById(@PathVariable academicMinistryId: String) =
            queryGateway.process(AcademicMinistryQuery.ById(academicMinistryId))

}