package org.bialydunajec.campedition.presentation.rest.v1.request

import java.time.LocalDate

data class CreateCampEditionRequest(
        val campEditionId: Int,
        val campEditionStartDate: LocalDate,
        val campEditionEndDate: LocalDate,
        val campEditionPrice: Double
)