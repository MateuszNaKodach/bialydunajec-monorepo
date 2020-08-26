package org.bialydunajec.registrations.dto

import org.bialydunajec.ddd.dto.sharedkernel.AgeRangeDto


data class CampersLimitationsDto(
        val ageRange: AgeRangeDto?
)
