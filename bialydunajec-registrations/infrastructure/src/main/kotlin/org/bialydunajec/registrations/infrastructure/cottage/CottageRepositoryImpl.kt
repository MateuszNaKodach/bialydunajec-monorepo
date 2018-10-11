package org.bialydunajec.registrations.infrastructure.cottage

import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
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

    override fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId)=
            jpaRepository.findAllByCampRegistrationsEditionId(campRegistrationsEditionId)

    override fun findByIdAndCampRegistrationsEditionId(cottageId: CottageId, campRegistrationsEditionId: CampRegistrationsEditionId): Cottage? =
            jpaRepository.findByAggregateIdAndCampRegistrationsEditionId(cottageId, campRegistrationsEditionId)

    override fun countByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId) =
            jpaRepository.countByCampRegistrationsEditionId(campRegistrationsEditionId)
}

internal interface CottageJpaRepository : JpaRepository<Cottage, CottageId>{
    fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Collection<Cottage>
    fun findByAggregateIdAndCampRegistrationsEditionId(cottageId: CottageId, campRegistrationsEditionId: CampRegistrationsEditionId): Cottage?
    fun countByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Long
}