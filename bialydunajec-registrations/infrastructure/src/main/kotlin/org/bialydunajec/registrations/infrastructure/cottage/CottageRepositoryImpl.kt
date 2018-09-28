package org.bialydunajec.registrations.infrastructure.cottage

import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.infrastructure.AbstractDomainRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CottageRepositoryImpl(
        jpaRepository: CottageJpaRepository
) : AbstractDomainRepository<Cottage, CottageId, CottageJpaRepository>(jpaRepository), CottageRepository {

    override fun findByCampEditionId(campEditionId: CampEditionId): Cottage? = jpaRepository.findByCampEditionId(campEditionId)
}

internal interface CottageJpaRepository : JpaRepository<Cottage, CottageId>{
    fun findByCampEditionId(campEditionId: CampEditionId): Cottage?
}