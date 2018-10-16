package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CamperPersonalDataDto(
        @NotNull
        val firstName: String,

        @NotNull
        val lastName: String,

        @NotBlank
        val gender: Gender,

        val pesel: String?,

        val birthDate: LocalDate?
)