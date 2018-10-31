package org.bialydunajec.registrations.infrastructure.camper.payment

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPayment
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentId
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentReadOnlyRepository
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampParticipationPaymentRepositoryImpl(
        jpaRepository: CampParticipationPaymentJpaRepository
) : AbstractDomainRepositoryImpl<CampParticipationPayment, CampParticipationPaymentId, CampParticipationPaymentJpaRepository>(jpaRepository),
        CampParticipationPaymentRepository, CampParticipationPaymentReadOnlyRepository {

    override fun findByCampParticipantId(campParticipantId: CampParticipantId) = jpaRepository.findByCampParticipantId(campParticipantId)
}

internal interface CampParticipationPaymentJpaRepository : JpaRepository<CampParticipationPayment, CampParticipationPaymentId> {
    fun findByCampParticipantId(campParticipantId: CampParticipantId): CampParticipationPayment?
}