package org.bialydunajec.registrations.infrastructure.camper

import org.bialydunajec.registrations.domain.camper.Camper
import org.bialydunajec.registrations.domain.camper.CamperId
import org.bialydunajec.registrations.domain.camper.CamperRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CamperRepositoryImpl(
        jpaRepository: CamperJpaRepository
) : AbstractDomainRepositoryImpl<Camper, CamperId, CamperJpaRepository>(jpaRepository), CamperRepository {

}

internal interface CamperJpaRepository : JpaRepository<Camper, CamperId> {
}