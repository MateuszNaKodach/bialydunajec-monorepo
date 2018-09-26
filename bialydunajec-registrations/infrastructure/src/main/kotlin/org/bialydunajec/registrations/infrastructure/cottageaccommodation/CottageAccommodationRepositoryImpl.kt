package org.bialydunajec.registrations.infrastructure.cottageaccommodation

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Pesel
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottageaccommodation.CottageAccommodation
import org.bialydunajec.registrations.domain.cottageaccommodation.CottageAccommodationId
import org.bialydunajec.registrations.domain.cottageaccommodation.CottageAccommodationRepository
import org.bialydunajec.registrations.infrastructure.AbstractDomainRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CottageAccommodationRepositoryImpl(
        cottageAccommodationJpaRepository: CottageAccommodationJpaRepository
) : AbstractDomainRepository<CottageAccommodation, CottageAccommodationId, CottageAccommodationJpaRepository>(cottageAccommodationJpaRepository), CottageAccommodationRepository {
    override fun findByCottageId(cottageId: CottageId): CottageAccommodation? = jpaRepository.findByCottageId(cottageId)

    override fun findByAccommodatedCamperPesel(pesel: Pesel): CottageAccommodation? = null //TODO
}

internal interface CottageAccommodationJpaRepository : JpaRepository<CottageAccommodation, CottageAccommodationId> {
    fun findByCottageId(cottageId: CottageId): CottageAccommodation?
}