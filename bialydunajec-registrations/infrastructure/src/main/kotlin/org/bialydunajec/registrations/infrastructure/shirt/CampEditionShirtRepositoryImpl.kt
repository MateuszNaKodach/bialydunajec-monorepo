package org.bialydunajec.registrations.infrastructure.shirt

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.shirt.CampEditionShirt
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtId
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtReadOnlyRepository
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

const val CAMP_EDITION_SHIRT_CACHE = "org.bialydunajec.campregistrations.CAMP_EDITION_SHIRT_CACHE"

@Repository
internal class CampEditionShirtRepositoryImpl(
        jpaRepository: CampEditionShirtJpaRepository
) : AbstractDomainRepositoryImpl<CampEditionShirt, CampEditionShirtId, CampEditionShirtJpaRepository>(jpaRepository),
        CampEditionShirtRepository, CampEditionShirtReadOnlyRepository {

    @Cacheable(cacheNames = [CAMP_EDITION_SHIRT_CACHE], key = "{#root.methodName,#aggregateId}")
    override fun findById(aggregateId: CampEditionShirtId): CampEditionShirt? =
            super.findById(aggregateId)

    @Cacheable(cacheNames = [CAMP_EDITION_SHIRT_CACHE], key = "{#root.methodName}")
    override fun findAll(): Collection<CampEditionShirt> =
            super.findAll()

    @CacheEvict(cacheNames = [CAMP_EDITION_SHIRT_CACHE], allEntries = true)
    override fun save(aggregateRoot: CampEditionShirt): CampEditionShirt =
            super.save(aggregateRoot)

    @Cacheable(cacheNames = [CAMP_EDITION_SHIRT_CACHE], key = "{#root.methodName,#campRegistrationsEditionId}")
    override fun findByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): CampEditionShirt? =
            jpaRepository.findByCampRegistrationsEditionId(campRegistrationsEditionId)
}

internal interface CampEditionShirtJpaRepository : JpaRepository<CampEditionShirt, CampEditionShirtId> {
    fun findByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): CampEditionShirt?
}