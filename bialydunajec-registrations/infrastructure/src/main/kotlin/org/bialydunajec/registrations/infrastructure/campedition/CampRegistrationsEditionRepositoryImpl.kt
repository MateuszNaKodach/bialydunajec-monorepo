package org.bialydunajec.registrations.infrastructure.campedition

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampRegistrationsEditionRepositoryImpl(
        jpaRepository: CampRegistrationsEditionJpaRepository
) : AbstractDomainRepositoryImpl<CampRegistrationsEdition, CampEditionId, CampRegistrationsEditionJpaRepository>(jpaRepository), CampRegistrationsEditionRepository {

}

internal interface CampRegistrationsEditionJpaRepository : JpaRepository<CampRegistrationsEdition, CampEditionId>