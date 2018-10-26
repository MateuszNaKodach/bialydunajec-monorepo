package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.dto.AgeRangeDto

data class CampersLimitationsDto(
        val ageRange: AgeRangeDto?
)