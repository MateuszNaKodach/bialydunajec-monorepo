package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.base.persistence.ReadOnlyDomainRepository
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId

interface CampParticipantCottageAccountReadOnlyRepository
    : ReadOnlyDomainRepository<CampParticipantCottageAccount, CampParticipantCottageAccountId> {

    fun findByCampParticipantIdAndCottageId(campParticipantId: CampParticipantId, cottageId: CottageId): CampParticipantCottageAccount?
}