package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface CampParticipantCottageAccountRepository
    : DomainRepository<CampParticipantCottageAccount, CampParticipantCottageAccountId> {
}