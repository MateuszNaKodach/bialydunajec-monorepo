package org.bialydunajec.campedition.application.query.api.dto

import org.bialydunajec.campedition.domain.campedition.valueobject.CampEditionSnapshot
import java.time.LocalDate

data class CampEditionDto(
        val campEditionId: String,
        val campEditionStartDate: LocalDate,
        val campEditionEndDate: LocalDate,
        val campEditionYear: Int,
        val campEditionPrice: Double
) {
    internal companion object {
        fun from(campEdition: CampEditionSnapshot) = CampEditionDto(
                campEditionId = campEdition.campEditionId.toString(),
                campEditionStartDate = campEdition.startDate,
                campEditionEndDate = campEdition.endDate,
                campEditionYear = campEdition.startDate.year,
                campEditionPrice = campEdition.price.getValue().toDouble()
        )
    }
}