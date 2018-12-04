package org.bialydunajec.registrations.readmodel.shirt

import org.bialydunajec.registrations.dto.ParticipationStatusDto
import org.bialydunajec.registrations.dto.ShirtTypeDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
internal class ShirtOrder(
        @Id
        val shirtOrderId: String,
        val campRegistrationsEditionId: String,
        val campParticipant: CampParticipant,
        val campEditionShirtId: String,
        val selectedOptions: SelectedOptions,
        val placedDate: Instant
) {

    internal data class CampParticipant(
            val campParticipantId: String,
            val cottage: Cottage,
            var pesel: String?,
            var firstName: String?,
            var lastName: String?,
            var emailAddress: String?,
            var phoneNumber: String?,
            var participationStatus: ParticipationStatusDto?
    )

    internal data class Cottage(
            val cottageId: String,
            val name: String
    )

    internal data class SelectedOptions(
            val shirtColorOptionId: String,
            val colorName: String,
            val shirtSizeOptionId: String,
            val sizeName: String,
            val shirtType: ShirtTypeDto
    )
}