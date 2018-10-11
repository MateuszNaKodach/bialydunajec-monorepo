package org.bialydunajec.registrations.application.query.api.dto

import org.bialydunajec.ddd.application.base.query.api.dto.AgeRangeDto

data class CampersLimitationsDto(
        val ageRange: AgeRangeDto?
)