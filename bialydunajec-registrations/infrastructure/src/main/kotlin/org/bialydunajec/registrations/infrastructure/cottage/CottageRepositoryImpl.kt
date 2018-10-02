package org.bialydunajec.registrations.infrastructure.cottage

import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CottageRepositoryImpl(
        jpaRepository: CottageJpaRepository
) : AbstractDomainRepositoryImpl<Cottage, CottageId, CottageJpaRepository>(jpaRepository), CottageRepository {

    override fun findByCampEditionId(campEditionId: CampEditionId)= jpaRepository.findByCampEditionId(campEditionId)

    override fun countByCampEditionId(campEditionId: CampEditionId) = jpaRepository.countByCampEditionId(campEditionId)
}

internal interface CottageJpaRepository : JpaRepository<Cottage, CottageId>{
    fun findByCampEditionId(campEditionId: CampEditionId): Cottage?
    fun countByCampEditionId(campEditionId: CampEditionId): Long
}