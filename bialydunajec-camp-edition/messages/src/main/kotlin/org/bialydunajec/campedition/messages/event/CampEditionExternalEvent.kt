package org.bialydunajec.campedition.messages.event

import java.time.LocalDate

sealed class CampEditionExternalEvent(
        val campEditionId: String
) {

    class CampEditionCreated(
            campEditionId: String,
            val startDate: LocalDate,
            val endDate: LocalDate
    ) : CampEditionExternalEvent(campEditionId)

    class CampEditionDurationUpdated(
            campEditionId: String,
            val startDate: LocalDate,
            val endDate: LocalDate
    ) : CampEditionExternalEvent(campEditionId)
}