package org.bialydunajec.registrations.infrastructure.cottage.conditions

import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.conditions.CottageConditions
import org.bialydunajec.registrations.domain.cottage.conditions.CottageConditionsRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CottageConditionsRepositoryImpl(private val jpaRepository: CottageConditionsJpaRepository) :
    CottageConditionsRepository {

    override fun save(aggregateRoot: CottageConditions): CottageConditions {
        return jpaRepository.save(aggregateRoot)
    }

    override fun findByCottageId(cottageId: CottageId): CottageConditions? {
        return jpaRepository.findById(cottageId).orElse(null)
    }
}

internal interface CottageConditionsJpaRepository : JpaRepository<CottageConditions, CottageId>
