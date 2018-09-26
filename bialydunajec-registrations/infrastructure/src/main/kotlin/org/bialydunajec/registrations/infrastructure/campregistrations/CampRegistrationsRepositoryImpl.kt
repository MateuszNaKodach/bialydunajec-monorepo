package org.bialydunajec.registrations.infrastructure.campregistrations

import org.bialydunajec.registrations.domain.campregistrations.CampRegistrations
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsRepository
import org.bialydunajec.registrations.infrastructure.AbstractDomainRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampRegistrationsRepositoryImpl(
        campRegistrationJpaRepository: CampRegistrationsJpaRepository
) : AbstractDomainRepository<CampRegistrations, CampRegistrationsId, CampRegistrationsJpaRepository>(campRegistrationJpaRepository), CampRegistrationsRepository {

}

internal interface CampRegistrationsJpaRepository : JpaRepository<CampRegistrations, CampRegistrationsId>