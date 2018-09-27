package org.bialydunajec.registrations.infrastructure.camper

import org.bialydunajec.registrations.domain.camper.Camper
import org.bialydunajec.registrations.domain.camper.CamperId
import org.bialydunajec.registrations.domain.camper.CamperRepository
import org.bialydunajec.registrations.infrastructure.AbstractDomainRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CamperRepositoryImpl(
        jpaRepository: CamperJpaRepository
) : AbstractDomainRepository<Camper, CamperId, CamperJpaRepository>(jpaRepository), CamperRepository {

}

internal interface CamperJpaRepository : JpaRepository<Camper, CamperId> {
}