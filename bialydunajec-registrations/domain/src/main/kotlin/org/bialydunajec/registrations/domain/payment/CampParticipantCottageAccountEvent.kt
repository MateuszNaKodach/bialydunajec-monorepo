package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.payment.entity.PaymentCommitmentId
import org.bialydunajec.registrations.domain.payment.valueobject.PaymentCommitmentSnapshot
import java.time.Instant

sealed class CampParticipantCottageAccountEvent : DomainEvent<CampParticipantCottageAccountId> {
    data class Created(
            override val aggregateId: CampParticipantCottageAccountId,
            val campParticipantId: CampParticipantId,
            val cottageId: CottageId,
            val campDownPaymentCommitmentSnapshot: PaymentCommitmentSnapshot?,
            val campParticipationCommitmentSnapshot: PaymentCommitmentSnapshot,
            val campBusCommitmentSnapshot: PaymentCommitmentSnapshot?
    ) : CampParticipantCottageAccountEvent()

    data class MoneyDeposited(
            override val aggregateId: CampParticipantCottageAccountId,
            val amount: Money,
            val description: String?,
            val createdDate: Instant
    ) : CampParticipantCottageAccountEvent()

    data class MoneyWithdrawn(
            override val aggregateId: CampParticipantCottageAccountId,
            val amount: Money,
            val description: String,
            val createdDate: Instant
    ) : CampParticipantCottageAccountEvent()

    data class CommitmentPaid(
            override val aggregateId: CampParticipantCottageAccountId,
            val paymentCommitmentId: PaymentCommitmentId
    ) : CampParticipantCottageAccountEvent()
}