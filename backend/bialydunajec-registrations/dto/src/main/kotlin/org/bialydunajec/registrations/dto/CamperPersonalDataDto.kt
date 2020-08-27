package org.bialydunajec.registrations.dto

import org.bialydunajec.ddd.dto.sharedkernel.GenderDto
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CamperPersonalDataDto(
        @NotNull
        val firstName: String,

        @NotNull
        val lastName: String,

        @NotBlank
        val gender: GenderDto,

        val pesel: String?,

        val birthDate: LocalDate?
)
