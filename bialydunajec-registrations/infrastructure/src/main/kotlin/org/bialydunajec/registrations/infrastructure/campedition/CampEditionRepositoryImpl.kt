package org.bialydunajec.registrations.infrastructure.campedition

import org.bialydunajec.registrations.domain.campedition.CampEdition
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.CampEditionRepository
import org.bialydunajec.registrations.infrastructure.AbstractDomainRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampEditionRepositoryImpl(
        jpaRepository: CampEditionJpaRepository
) : AbstractDomainRepository<CampEdition, CampEditionId, CampEditionJpaRepository>(jpaRepository), CampEditionRepository {

}

internal interface CampEditionJpaRepository : JpaRepository<CampEdition, CampEditionId>