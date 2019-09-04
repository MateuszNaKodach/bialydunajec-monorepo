package org.bialydunajec.registrations.infrastructure.shirt

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.shirt.*
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

const val SHIRT_ORDER_CACHE = "org.bialydunajec.campregistrations.SHIRT_ORDER_CACHE"

@Repository
internal class ShirtOrderRepositoryImpl(
        jpaRepository: ShirtOrderJpaRepository
) : AbstractDomainRepositoryImpl<ShirtOrder, ShirtOrderId, ShirtOrderJpaRepository>(jpaRepository),
        ShirtOrderRepository {

    override fun findByCampParticipantId(campParticipantId: CampParticipantId) =
        jpaRepository.findByCampParticipantId(campParticipantId)


    @Cacheable(cacheNames = [SHIRT_ORDER_CACHE], key = "{#root.methodName,#aggregateId}")
    override fun findById(aggregateId: ShirtOrderId): ShirtOrder? =
            super.findById(aggregateId)

    @Cacheable(cacheNames = [SHIRT_ORDER_CACHE], key = "{#root.methodName}")
    override fun findAll(): Collection<ShirtOrder> =
            super.findAll()

    @CacheEvict(cacheNames = [SHIRT_ORDER_CACHE], allEntries = true)
    override fun save(aggregateRoot: ShirtOrder): ShirtOrder =
            super.save(aggregateRoot)
}

internal interface ShirtOrderJpaRepository : JpaRepository<ShirtOrder, ShirtOrderId> {
    fun findByCampParticipantId(campParticipantId: CampParticipantId): ShirtOrder?
}