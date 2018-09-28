package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.aggregate.DomainRepository
import org.bialydunajec.registrations.domain.campedition.CampEditionId

interface CottageRepository : DomainRepository<Cottage, CottageId>{
    fun findByCampEditionId(campEditionId: CampEditionId): Cottage?
}