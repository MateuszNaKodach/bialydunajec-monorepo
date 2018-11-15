package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class CampParticipantCottageAccountEvent : DomainEvent<CampParticipantCottageAccountId> {
    data class Created(
            override val aggregateId: CampParticipantCottageAccountId
    ) : CampParticipantCottageAccountEvent()
}