package org.bialydunajec.registrations.application.query.api

import org.bialydunajec.registrations.application.query.readmodel.CampRegistrationsDomainModelReader
import org.springframework.stereotype.Component

@Component
class CampRegistrationsQueryGateway internal constructor(private val domainModelReader: CampRegistrationsDomainModelReader) {
    fun process(query: CampRegistrationsEditionQuery.ById) = domainModelReader.readFor(query)
    fun process(query: CampRegistrationsEditionQuery.All) = domainModelReader.readFor(query)
    fun process(query: CampRegistrationsEditionQuery.InProgress) = domainModelReader.readFor(query)

    fun process(query: CampEditionQuery.All) = domainModelReader.readFor(query)
    fun process(query: CampEditionQuery.ById) = domainModelReader.readFor(query)

    fun process(query: AcademicMinistryQuery.All) = domainModelReader.readFor(query)
    fun process(query: AcademicMinistryQuery.ById) = domainModelReader.readFor(query)

    fun process(query: CottageQuery.All) = domainModelReader.readFor(query)
    fun process(query: CottageQuery.ById) = domainModelReader.readFor(query)
    fun process(query: CottageQuery.AllByCampRegistrationsEditionId) = domainModelReader.readFor(query)
    fun process(query: CottageQuery.ByIdAndByCampRegistrationsEditionId) = domainModelReader.readFor(query)
    fun process(query: CottageQuery.AllActiveByCampRegistrationsEditionId) = domainModelReader.readFor(query)

    fun process(query: CampParticipantQuery.CountByCottageId) = domainModelReader.readFor(query)
}
