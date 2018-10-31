package org.bialydunajec.registrations.domain.camper.payment

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface CampParticipationPaymentRepository
    : DomainRepository<CampParticipationPayment, CampParticipationPaymentId>, CampParticipationPaymentReadOnlyRepository