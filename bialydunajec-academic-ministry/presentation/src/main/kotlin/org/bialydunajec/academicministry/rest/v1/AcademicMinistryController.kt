package org.bialydunajec.academicministry.rest.v1

import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQuery
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api/v1/academic-ministry")
internal class AcademicMinistryController(
        private val academicMinistryQueryGateway: AcademicMinistryQueryGateway
) {

    @GetMapping
    fun getAllAcademicMinistries() =
            academicMinistryQueryGateway.process(AcademicMinistryQuery.All())
                    .sortedBy { it.getDisplayName() }

    @GetMapping("/name")
    fun getAllAcademicMinistriesNames() =
            academicMinistryQueryGateway.process(AcademicMinistryQuery.NamesForAll())
                    .sortedBy { it.getDisplayName() }

    @GetMapping("/{academicMinistryId}")
    fun getAcademicMinistryById(@PathVariable academicMinistryId: String) =
            academicMinistryQueryGateway.process(AcademicMinistryQuery.ById(academicMinistryId))
}