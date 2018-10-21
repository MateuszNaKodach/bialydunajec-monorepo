package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.persistence.ReadOnlyDomainRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId

interface CampEditionShirtReadOnlyRepository : ReadOnlyDomainRepository<CampEditionShirt, CampEditionShirtId> {

    fun findByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): CampEditionShirt?

}