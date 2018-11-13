package org.bialydunajec.registrations.application.dto

import org.bialydunajec.registrations.domain.campedition.valueobject.CampRegistrationsEditionSnapshot
import java.time.LocalDate

data class CampEditionDto(
        val campEditionId: String,
        val campEditionStartDate: LocalDate,
        val campEditionEndDate: LocalDate,
        val campEditionYear: Int
) {
    internal companion object {
        fun from(snapshot: CampRegistrationsEditionSnapshot) = CampEditionDto(
                campEditionId = snapshot.campRegistrationsEditionId.toString(),
                campEditionStartDate = snapshot.editionStartDate,
                campEditionEndDate = snapshot.editionEndDate,
                campEditionYear = snapshot.editionStartDate.year
        )
    }
}