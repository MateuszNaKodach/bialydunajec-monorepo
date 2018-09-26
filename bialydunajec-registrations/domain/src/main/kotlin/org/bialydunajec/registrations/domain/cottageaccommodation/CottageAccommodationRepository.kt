package org.bialydunajec.registrations.domain.cottageaccommodation

import org.bialydunajec.ddd.domain.base.aggregate.DomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Pesel
import org.bialydunajec.registrations.domain.cottage.CottageId

interface CottageAccommodationRepository : DomainRepository<CottageAccommodation, CottageAccommodationId> {
    fun findByCottageId(cottageId: CottageId): CottageAccommodation?
    fun findByAccommodatedCamperPesel(pesel: Pesel): CottageAccommodation?
}