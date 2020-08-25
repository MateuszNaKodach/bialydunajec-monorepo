package org.bialydunajec.academicministry.application.query.api

import org.bialydunajec.academicministry.application.dto.AcademicMinistryDto
import org.bialydunajec.academicministry.application.dto.toDto
import org.bialydunajec.academicministry.application.query.readmodel.AcademicMinistryDomainModelReader

class AcademicMinistryAdminQueryGateway internal constructor(
        private val domainModelReader: AcademicMinistryDomainModelReader
) {
    fun process(query: AcademicMinistryQuery.All) =
            domainModelReader.readFor(query)
                    .map { AcademicMinistryDto.from(it) }

    fun process(query: AcademicMinistryQuery.ById) =
            domainModelReader.readFor(query)
                    ?.let { AcademicMinistryDto.from(it) }

    fun process(query: AcademicPriestQuery.AllByAcademicMinistryId) =
            domainModelReader.readFor(query)
                    .map { it.toDto() }

}
