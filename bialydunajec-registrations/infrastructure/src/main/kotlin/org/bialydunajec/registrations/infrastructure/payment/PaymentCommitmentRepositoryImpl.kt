package org.bialydunajec.registrations.infrastructure.payment

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPayment
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentId
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentReadOnlyRepository
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentRepository
import org.bialydunajec.registrations.domain.payment.PaymentCommitment
import org.bialydunajec.registrations.domain.payment.PaymentCommitmentId
import org.bialydunajec.registrations.domain.payment.PaymentCommitmentReadOnlyRepository
import org.bialydunajec.registrations.domain.payment.PaymentCommitmentRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class PaymentCommitmentRepositoryImpl(
        jpaRepository: PaymentCommitmentJpaRepository
) : AbstractDomainRepositoryImpl<PaymentCommitment, PaymentCommitmentId, PaymentCommitmentJpaRepository>(jpaRepository),
        PaymentCommitmentRepository, PaymentCommitmentReadOnlyRepository {

}

internal interface PaymentCommitmentJpaRepository : JpaRepository<PaymentCommitment, PaymentCommitmentId> {
}