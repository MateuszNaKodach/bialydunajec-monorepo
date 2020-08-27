package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId

class CampParticipantCottageAccountId(campParticipantCottageAccountId: String = defaultValue())
    : AggregateId(campParticipantCottageAccountId)
