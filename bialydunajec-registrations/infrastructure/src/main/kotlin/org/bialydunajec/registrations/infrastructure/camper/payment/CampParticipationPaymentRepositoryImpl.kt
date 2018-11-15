package org.bialydunajec.registrations.infrastructure.camper.payment

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.payment.entity.PaymentCommitment
import org.bialydunajec.registrations.domain.payment.entity.PaymentCommitmentId
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentReadOnlyRepository
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampParticipationPaymentRepositoryImpl(
        jpaRepository: CampParticipationPaymentJpaRepository
) : AbstractDomainRepositoryImpl<PaymentCommitment, PaymentCommitmentId, CampParticipationPaymentJpaRepository>(jpaRepository),
        CampParticipationPaymentRepository, CampParticipationPaymentReadOnlyRepository {

    override fun findByCampParticipantId(campParticipantId: CampParticipantId) = jpaRepository.findByCampParticipantId(campParticipantId)
}

internal interface CampParticipationPaymentJpaRepository : JpaRepository<PaymentCommitment, PaymentCommitmentId> {
    fun findByCampParticipantId(campParticipantId: CampParticipantId): PaymentCommitment?
}