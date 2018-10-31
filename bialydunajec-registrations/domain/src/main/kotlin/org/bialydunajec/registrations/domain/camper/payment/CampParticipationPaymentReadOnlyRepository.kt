package org.bialydunajec.registrations.domain.camper.payment

import org.bialydunajec.ddd.domain.base.persistence.ReadOnlyDomainRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId

interface CampParticipationPaymentReadOnlyRepository
    : ReadOnlyDomainRepository<CampParticipationPayment, CampParticipationPaymentId> {
    fun findByCampParticipantId(campParticipantId: CampParticipantId): CampParticipationPayment?
}