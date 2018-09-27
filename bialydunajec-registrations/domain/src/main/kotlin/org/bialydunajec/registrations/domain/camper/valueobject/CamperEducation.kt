package org.bialydunajec.registrations.domain.camper.valueobject

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Embeddable
data class CamperEducation(
        @NotNull
        @NotBlank
        val university: String,

        @NotNull
        @NotBlank
        val faculty: String,

        @NotNull
        @NotBlank
        val fieldOfStudy: String,

        @NotBlank
        val highSchool: String?,

        @NotNull
        val isHighSchoolRecentGraduate: Boolean
)