package org.bialydunajec.registrations.domain.camper.payment

import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class CampParticipationPaymentEvent : DomainEvent<CampParticipationPaymentId> {
    data class Created(
            override val aggregateId: CampParticipationPaymentId
    ) : CampParticipationPaymentEvent()
}