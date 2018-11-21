package org.bialydunajec.registrations.application.dto

import java.time.LocalDate

data class CampEditionDto(
        val campEditionId: String,
        val campEditionStartDate: LocalDate,
        val campEditionEndDate: LocalDate,
        val campEditionYear: Int,
        val editionPrice: Double,
        val editionDownPaymentAmount: Double?
)