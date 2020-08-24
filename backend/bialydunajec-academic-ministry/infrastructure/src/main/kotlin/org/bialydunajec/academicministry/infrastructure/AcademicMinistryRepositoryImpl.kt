package org.bialydunajec.academicministry.infrastructure

import org.bialydunajec.academicministry.domain.AcademicMinistry
import org.bialydunajec.academicministry.domain.AcademicMinistryId
import org.bialydunajec.academicministry.domain.AcademicMinistryRepository
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
// FIXME: Watch out on lazy collections if entity is cached!
const val ACADEMIC_MINISTRY_CACHE = "org.bialydunajec.campregistrations.ACADEMIC_MINISTRY_CACHE"

@Repository
internal class AcademicMinistryRepositoryImpl(
        jpaRepository: AcademicMinistryJpaRepository,
        domainEventBus: DomainEventBus
) : AbstractDomainRepositoryImpl<AcademicMinistry, AcademicMinistryId, AcademicMinistryJpaRepository>(jpaRepository, domainEventBus), AcademicMinistryRepository {

    @Cacheable(cacheNames = [ACADEMIC_MINISTRY_CACHE], key = "#root.methodName")
    override fun findAll(): Collection<AcademicMinistry> = super.findAll()

    @Cacheable(cacheNames = [ACADEMIC_MINISTRY_CACHE], key = "{#root.methodName, #aggregateId}")
    override fun findById(aggregateId: AcademicMinistryId): AcademicMinistry? = super.findById(aggregateId)

    @CacheEvict(cacheNames = [ACADEMIC_MINISTRY_CACHE], allEntries = true)
    override fun save(aggregateRoot: AcademicMinistry): AcademicMinistry = super.save(aggregateRoot)

}

internal interface AcademicMinistryJpaRepository : JpaRepository<AcademicMinistry, AcademicMinistryId>
