package org.bialydunajec.academicministry.application.dto

import org.bialydunajec.ddd.domain.base.validation.constraints.NullOrNotBlank

data class PersonalTitleDto(
        @field:NullOrNotBlank
        val name: String?,

        @field:NullOrNotBlank
        val prefix: String?,

        @field:NullOrNotBlank
        val postfix: String?
)