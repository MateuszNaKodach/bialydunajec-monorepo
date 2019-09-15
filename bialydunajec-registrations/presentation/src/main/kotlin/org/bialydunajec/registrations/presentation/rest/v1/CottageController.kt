package org.bialydunajec.registrations.presentation.rest.v1

import org.bialydunajec.registrations.application.query.api.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/camp-registrations/cottage")
class CottageController(private val queryGateway: CampRegistrationsQueryGateway) {


    @GetMapping("/newest")
    fun getNewestByAcademicMinistryId(@RequestParam academicMinistryId: String) =
        queryGateway.process(CottageQuery.NewestByAcademicMinistryId(academicMinistryId))

}
