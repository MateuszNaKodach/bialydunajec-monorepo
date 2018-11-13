package org.bialydunajec.registrations.application.dto

import java.time.LocalDate
import java.time.LocalTime

data class StayDurationDto(
        val checkInDate: LocalDate?,
        val checkInTime: LocalTime?,
        val checkOutDate: LocalDate?,
        val checkOutTime: LocalTime?
)