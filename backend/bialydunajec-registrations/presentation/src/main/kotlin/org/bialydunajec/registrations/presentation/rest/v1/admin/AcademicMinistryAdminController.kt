package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.query.api.AcademicMinistryQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsQueryGateway
import org.springframework.web.bind.annotation.*

//TODO: Change to /admin-rest-api/v1
@RestController("registrationsAcademicMinistryAdminController")
@RequestMapping("/rest-api/v1/admin/camp-registrations")
class AcademicMinistryAdminController(
        private val commandGateway: CampRegistrationsAdminCommandGateway,
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