package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.registrations.domain.campedition.CampEditionId

interface CottageRepository : DomainRepository<Cottage, CottageId> {
    fun findByCampEditionId(campEditionId: CampEditionId): Cottage?
    fun countByCampEditionId(campEditionId: CampEditionId): Long
}