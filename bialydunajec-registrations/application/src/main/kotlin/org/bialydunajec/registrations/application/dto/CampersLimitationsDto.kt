package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.query.api.dto.AgeRangeDto

data class CampersLimitationsDto(
        val ageRange: AgeRangeDto?
)