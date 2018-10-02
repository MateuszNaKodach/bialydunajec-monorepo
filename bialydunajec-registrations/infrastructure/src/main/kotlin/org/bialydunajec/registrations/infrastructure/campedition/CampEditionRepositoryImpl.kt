package org.bialydunajec.registrations.infrastructure.campedition

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.campedition.CampEdition
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.CampEditionRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampEditionRepositoryImpl(
        jpaRepository: CampEditionJpaRepository
) : AbstractDomainRepositoryImpl<CampEdition, CampEditionId, CampEditionJpaRepository>(jpaRepository), CampEditionRepository {

}

internal interface CampEditionJpaRepository : JpaRepository<CampEdition, CampEditionId>