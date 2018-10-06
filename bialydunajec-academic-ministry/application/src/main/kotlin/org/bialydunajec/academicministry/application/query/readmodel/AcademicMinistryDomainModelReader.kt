package org.bialydunajec.academicministry.application.query.readmodel

import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQuery
import org.bialydunajec.academicministry.domain.AcademicMinistryId
import org.bialydunajec.academicministry.domain.AcademicMinistryRepository
import org.springframework.stereotype.Component

@Component
internal class AcademicMinistryDomainModelReader(private val repository: AcademicMinistryRepository) {

    fun readFor(query: AcademicMinistryQuery.All) =
            repository.findAll()
                    .map { it.getSnapshot() }

    fun readFor(query: AcademicMinistryQuery.ById) =
            repository.findById(AcademicMinistryId(query.academicMinistryId))?.getSnapshot()
}