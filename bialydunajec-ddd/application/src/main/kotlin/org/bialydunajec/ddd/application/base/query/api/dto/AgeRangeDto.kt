package org.bialydunajec.ddd.application.base.query.api.dto

import javax.validation.constraints.Positive

data class AgeRangeDto(
        @Positive
        val min: Int?,
        @Positive
        val max: Int?
)