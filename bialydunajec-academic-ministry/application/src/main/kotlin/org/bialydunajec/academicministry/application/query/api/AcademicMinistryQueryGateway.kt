package org.bialydunajec.academicministry.application.query.api

import org.bialydunajec.academicministry.application.query.readmodel.AcademicMinistryDomainModelReader

class AcademicMinistryQueryGateway internal constructor(
        private val domainModelReader: AcademicMinistryDomainModelReader
) {
    fun process(query: AcademicMinistryQuery.All) =
            domainModelReader.readFor(query)
                    .map { it }

    fun process(query: AcademicMinistryQuery.ById) =
            domainModelReader.readFor(query)


}