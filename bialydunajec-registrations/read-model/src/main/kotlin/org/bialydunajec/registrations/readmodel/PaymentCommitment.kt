package org.bialydunajec.registrations.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
internal data class PaymentCommitment(
        @Id
        val paymentCommitmentId: String,
        val campParticipant: CampParticipant,
        val cottage: Cottage,
        val amount: Double,
        val currency: String,
        val description: String,
        val deadlineDate: Instant,
        val paid: Boolean,
        val active: Boolean
) {

    internal data class CampParticipant(
            val campParticipantId: String,
            val pesel: String,
            val firstName: String,
            val lastName: String,
            val emailAddress: String,
            val phoneNumber: String
    )

    internal data class Cottage(
            val cottageId: String,
            val name: String
    )
}

