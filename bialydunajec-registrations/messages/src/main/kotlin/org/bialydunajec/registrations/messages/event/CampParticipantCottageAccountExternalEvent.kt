package org.bialydunajec.registrations.messages.event

import java.time.Instant

sealed class CampParticipantCottageAccountExternalEvent {

    data class Created(
            val campParticipantCottageAccountId: String,
            val campParticipant: CampParticipant?,
            val cottage: Cottage?,
            val campRegistrationsEditionId: String?,
            val campDownPaymentCommitmentSnapshot: PaymentCommitmentSnapshot?,
            val campParticipationCommitmentSnapshot: PaymentCommitmentSnapshot,
            val campBusCommitmentSnapshot: PaymentCommitmentSnapshot?
    ) : CampParticipantCottageAccountExternalEvent() {
        class PaymentCommitmentSnapshot(
                val paymentCommitmentId: String,
                val initialAmount: Double,
                val description: String?,
                val deadlineDate: Instant?,
                val amount: Double,
                val paid: Boolean
        )

        class Cottage(
                val cottageId: String,
                val name: String
        )

        class CampParticipant(
                val campParticipantId: String,
                val firstName: String?,
                val lastName: String?,
                val emailAddress: String?,
                val phoneNumber: String?
        )
    }

    data class MoneyDeposited(
            val campParticipantCottageAccountId: String,
            val amount: Double,
            val description: String?,
            val createdDate: Instant
    ) : CampParticipantCottageAccountExternalEvent()

    data class MoneyWithdrawn(
            val campParticipantCottageAccountId: String,
            val amount: Double,
            val description: String,
            val createdDate: Instant
    ) : CampParticipantCottageAccountExternalEvent()

    data class CommitmentPaid(
            val campParticipantCottageAccountId: String,
            val paymentCommitmentId: String
    ) : CampParticipantCottageAccountExternalEvent()
}