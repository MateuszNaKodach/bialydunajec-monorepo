package org.bialydunajec.campedition.infrastructure.campedition

import org.bialydunajec.campedition.domain.campedition.CampEdition
import org.bialydunajec.campedition.domain.campedition.CampEditionId
import org.bialydunajec.campedition.domain.campedition.CampEditionRepository
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampEditionRepositoryImpl(
        jpaRepository: CampEditionJpaRepository,
        domainEventBus: DomainEventBus
) : AbstractDomainRepositoryImpl<CampEdition, CampEditionId, CampEditionJpaRepository>(jpaRepository, domainEventBus), CampEditionRepository {

}

internal interface CampEditionJpaRepository : JpaRepository<CampEdition, CampEditionId>
