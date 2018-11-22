package org.bialydunajec.registrations.messages.event

import org.bialydunajec.registrations.dto.ParticipationStatusDto
import org.bialydunajec.registrations.dto.ShirtTypeDto
import java.time.Instant


sealed class ShirtOrderExternalEvent {

    data class OrderPlaced(
            val campRegistrationsEditionId: String,
            val shirtOrderId: String,
            val campParticipant: CampParticipant,
            val shirtOrder: ShirtOrder,
            val placedDate: Instant
    ) : ShirtOrderExternalEvent() {

        class CampParticipant(
                val campParticipantId: String,
                val pesel: String?,
                val firstName: String?,
                val lastName: String?,
                val emailAddress: String?,
                val phoneNumber: String?,
                val cottage: Cottage,
                val participationStatus: ParticipationStatusDto
        )

        class Cottage(
                val cottageId: String,
                val name: String
        )

        class ShirtOrder(
                val campEditionShirtId: String,
                val shirtColorOptionId: String,
                val colorName: String,
                val shirtSizeOptionId: String,
                val sizeName: String,
                val shirtType: ShirtTypeDto
        )
    }

}