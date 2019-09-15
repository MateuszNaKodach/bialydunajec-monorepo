package org.bialydunajec.registrations.infrastructure.cottage

import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageStatus
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

const val COTTAGE_CACHE = "org.bialydunajec.campregistrations.COTTAGE_CACHE"

@Repository
internal class CottageRepositoryImpl(
    jpaRepository: CottageJpaRepository
) : AbstractDomainRepositoryImpl<Cottage, CottageId, CottageJpaRepository>(jpaRepository), CottageRepository {

    @Cacheable(cacheNames = [COTTAGE_CACHE], key = "{#root.methodName,#campRegistrationsEditionId}")
    override fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId) =
        jpaRepository.findAllByCampRegistrationsEditionId(campRegistrationsEditionId)

    override fun findAllByCampRegistrationsEditionIdAndStatus(
        campRegistrationsEditionId: CampRegistrationsEditionId,
        status: CottageStatus
    ) = jpaRepository.findAllByCampRegistrationsEditionIdAndStatus(campRegistrationsEditionId, status)

    override fun findByIdAndCampRegistrationsEditionId(
        cottageId: CottageId,
        campRegistrationsEditionId: CampRegistrationsEditionId
    ): Cottage? = jpaRepository.findByAggregateIdAndCampRegistrationsEditionId(cottageId, campRegistrationsEditionId)

    override fun countByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId) =
        jpaRepository.countByCampRegistrationsEditionId(campRegistrationsEditionId)

    @CacheEvict(cacheNames = [COTTAGE_CACHE], allEntries = true)
    override fun save(aggregateRoot: Cottage) = super.save(aggregateRoot)

    @Cacheable(cacheNames = [COTTAGE_CACHE], key = "{#root.methodName,#aggregateId}")
    override fun findById(aggregateId: CottageId) = super.findById(aggregateId)

    @Cacheable(cacheNames = [COTTAGE_CACHE], key = "{#root.methodName,#academicMinistryId}")
    override fun findNewestCottageByAcademicMinistryId(academicMinistryId: AcademicMinistryId): Cottage? =
        jpaRepository.findFirstByAcademicMinistryIdOrderByCampRegistrationsEditionIdDesc(academicMinistryId)

}

internal interface CottageJpaRepository : JpaRepository<Cottage, CottageId> {
    fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Collection<Cottage>

    fun findAllByCampRegistrationsEditionIdAndStatus(
        campRegistrationsEditionId: CampRegistrationsEditionId,
        status: CottageStatus
    ): Collection<Cottage>

    fun findByAggregateIdAndCampRegistrationsEditionId(
        cottageId: CottageId,
        campRegistrationsEditionId: CampRegistrationsEditionId
    ): Cottage?

    fun countByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Long
    fun findFirstByAcademicMinistryIdOrderByCampRegistrationsEditionIdDesc(academicMinistryId: AcademicMinistryId): Cottage?
}
