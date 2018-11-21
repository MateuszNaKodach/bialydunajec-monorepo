package org.bialydunajec.registrations.application.dto

import org.bialydunajec.registrations.domain.campedition.valueobject.CampRegistrationsSnapshot
import java.time.LocalDate
import java.time.ZonedDateTime

data class CampRegistrationsEditionDto(
        val campRegistrationsEditionId: String,
        val editionStartDate: LocalDate,
        val editionEndDate: LocalDate,
        val editionPrice: Double,
        val editionDownPaymentAmount: Double?,
        val campRegistrations: CampRegistrationsDto
) {
    companion object {
    }
}

data class CampRegistrationsDto(
        val campRegistrationsId: String,
        val status: String,
        val timerStartDate: ZonedDateTime?,
        val timerEndDate: ZonedDateTime?,
        val lastStartedAt: ZonedDateTime?,
        val lastSuspendAt: ZonedDateTime?,
        val lastUnsuspendAt: ZonedDateTime?,
        val lastFinishedAt: ZonedDateTime?
) {
    companion object {
    }
}