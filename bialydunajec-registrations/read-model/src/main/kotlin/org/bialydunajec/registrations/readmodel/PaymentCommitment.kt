package org.bialydunajec.registrations.readmodel

import com.fasterxml.jackson.annotation.JsonFormat
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
        val campParticipantCottageAccountId: String?,
        var cottage: Cottage?,
        var amount: Double?,
        //val currency: String,
        var description: String?,

        @JsonFormat(timezone="Europe/Warsaw")
        var deadlineDate: Instant?,
        var isPaid: Boolean?,

        @JsonFormat(timezone="Europe/Warsaw")
        var paidDate: Instant?
) {

    companion object {
        fun onlyId(paymentCommitmentId: String) =
                PaymentCommitment(paymentCommitmentId, null, null, null, null, null, null, null, null, null,null)
    }

    internal enum class Type {
        CAMP_DOWN_PAYMENT,
        CAMP_PARTICIPATION,
        CAMP_BUS
    }

    internal data class CampParticipant(
            val campParticipantId: String,
            var pesel: String?,
            var firstName: String?,
            var lastName: String?,
            var emailAddress: String?,
            var phoneNumber: String?
    )
/*
    internal data class CampParticipantCottageAccount(
            val campParticipantCottageAccount: String,
            val isActiveCampParticipantAccount: String
    )*/

    internal data class Cottage(
            val cottageId: String,
            var name: String
    )
}

