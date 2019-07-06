package org.bialydunajec.campedition.infrastructure.persistence.jpa

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampEditionRepositoryImpl(
        jpaRepository: CampEditionJpaRepository
) : AbstractDomainRepositoryImpl<DbCampEdition, DbCampEditionId, CampEditionJpaRepository>(jpaRepository), CampEditionRepository {

}

internal interface CampEditionJpaRepository : JpaRepository<DbCampEdition, DbCampEditionId>
