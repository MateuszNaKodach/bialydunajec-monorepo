package org.bialydunajec.registrations.application.dto

import java.time.LocalDate
import java.time.LocalTime

data class StayDurationDto constructor(
        val checkInDate: LocalDate? = null,
        val checkInTime: LocalTime? = null,
        val checkOutDate: LocalDate? = null,
        val checkOutTime: LocalTime? = null
)