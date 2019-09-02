package org.bialydunajec.registrations.domain.camper.valueobject

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Embeddable
data class CamperEducation(
        @NotBlank
        private val university: String,

        @NotBlank
        private val faculty: String,

        @NotBlank
        private val fieldOfStudy: String,

        @NotBlank
        private val highSchool: String?,

        @NotNull
        private val isHighSchoolRecentGraduate: Boolean
) {
        fun getUniversity() = university
        fun getFaculty() = faculty
        fun getFieldOfStudy() = fieldOfStudy
        fun getHighSchool() = highSchool
        fun getIsHighSchoolRecentGraduate() = isHighSchoolRecentGraduate
}