package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId

class CampParticipantCottageAccountId(campParticipantCottageAccountId: String = defaultValue())
    : AggregateId(campParticipantCottageAccountId)