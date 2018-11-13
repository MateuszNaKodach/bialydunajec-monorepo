package org.bialydunajec.registrations.domain.camper.valueobject

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Embeddable
data class CamperEducation(
        @NotBlank
        val university: String,

        @NotBlank
        val faculty: String,

        @NotBlank
        val fieldOfStudy: String,

        @NotBlank
        val highSchool: String?,

        @NotNull
        val isHighSchoolRecentGraduate: Boolean
)