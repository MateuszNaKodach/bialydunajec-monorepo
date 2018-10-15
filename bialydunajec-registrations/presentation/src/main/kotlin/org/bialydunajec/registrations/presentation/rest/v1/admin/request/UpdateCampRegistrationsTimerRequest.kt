package org.bialydunajec.registrations.presentation.rest.v1.admin.request

import java.time.ZonedDateTime

data class UpdateCampRegistrationsTimerRequest(
        val timerStartDate: ZonedDateTime?,
        val timerEndDate: ZonedDateTime?
)