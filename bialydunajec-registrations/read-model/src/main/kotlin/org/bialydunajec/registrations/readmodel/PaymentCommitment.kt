package org.bialydunajec.registrations.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "singlePaymentCommitmentReadModels")
internal data class PaymentCommitment(
        @Id
        val paymentCommitmentId: String,
        val type: Type?,
        val campRegistrationsEditionId: String?,
        val campParticipant: CampParticipant?,
        val cottage: Cottage?,
        val amount: Double?,
        //val currency: String,
        val description: String?,
        val deadlineDate: Instant?,
        val isPaid: Boolean?
) {

    internal enum class Type {
        CAMP_DOWN_PAYMENT,
        CAMP_PARTICIPATION,
        CAMP_BUS
    }

    internal data class CampParticipant(
            val campParticipantId: String,
            val firstName: String?,
            val lastName: String?,
            val emailAddress: String?,
            val phoneNumber: String?
    )
/*
    internal data class CampParticipantCottageAccount(
            val campParticipantCottageAccount: String,
            val isActiveCampParticipantAccount: String
    )*/

    internal data class Cottage(
            val cottageId: String,
            val name: String
    )
}

