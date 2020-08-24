package org.bialydunajec.registrations.infrastructure.campedition

import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

const val CAMP_REGISTRATIONS_EDITION_CACHE = "org.bialydunajec.campregistrations.CAMP_REGISTRATIONS_EDITION_CACHE"

@Repository
internal class CampRegistrationsEditionRepositoryImpl(
        jpaRepository: CampRegistrationsEditionJpaRepository,
        domainEventBus: DomainEventBus
) : AbstractDomainRepositoryImpl<CampRegistrationsEdition, CampRegistrationsEditionId, CampRegistrationsEditionJpaRepository>(jpaRepository, domainEventBus),
        CampRegistrationsEditionRepository {

    @Cacheable(cacheNames = [CAMP_REGISTRATIONS_EDITION_CACHE], key = "{#root.methodName,#aggregateId}")
    override fun findById(aggregateId: CampRegistrationsEditionId): CampRegistrationsEdition? =
            super.findById(aggregateId)

    @Cacheable(cacheNames = [CAMP_REGISTRATIONS_EDITION_CACHE], key = "{#root.methodName}")
    override fun findAll(): Collection<CampRegistrationsEdition> =
            super.findAll()

    @CacheEvict(cacheNames = [CAMP_REGISTRATIONS_EDITION_CACHE], allEntries = true)
    override fun save(aggregateRoot: CampRegistrationsEdition): CampRegistrationsEdition =
            super.save(aggregateRoot)


}

internal interface CampRegistrationsEditionJpaRepository : JpaRepository<CampRegistrationsEdition, CampRegistrationsEditionId>
