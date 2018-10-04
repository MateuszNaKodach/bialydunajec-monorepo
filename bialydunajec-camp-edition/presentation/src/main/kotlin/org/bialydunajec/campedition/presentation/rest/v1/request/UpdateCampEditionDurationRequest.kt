package org.bialydunajec.campedition.presentation.rest.v1.request

import java.time.LocalDate

data class UpdateCampEditionDurationRequest(
        val campEditionStartDate: LocalDate,
        val campEditionEndDate: LocalDate
)