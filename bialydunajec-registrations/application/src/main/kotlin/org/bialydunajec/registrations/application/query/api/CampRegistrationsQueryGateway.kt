package org.bialydunajec.registrations.application.query.api

import org.bialydunajec.registrations.application.query.readmodel.CampRegistrationsDomainModelReader
import org.springframework.stereotype.Component

@Component
class CampRegistrationsQueryGateway internal constructor(private val domainModelReader: CampRegistrationsDomainModelReader) {
    fun process(query: CampRegistrationsEditionQuery.ById) = domainModelReader.readFor(query)
    fun process(query: CampRegistrationsEditionQuery.All) = domainModelReader.readFor(query)

    fun process(query: CampEditionQuery.All) = domainModelReader.readFor(query)
    fun process(query: CampEditionQuery.ById) = domainModelReader.readFor(query)

    fun process(query: AcademicMinistryQuery.All) = domainModelReader.readFor(query)
    fun process(query: AcademicMinistryQuery.ById) = domainModelReader.readFor(query)

}