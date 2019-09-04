package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId

interface ShirtOrderRepository : DomainRepository<ShirtOrder, ShirtOrderId>, ShirtOrderReadOnlyRepository{
    fun findByCampParticipantId(campParticipantId: CampParticipantId): ShirtOrder?
}