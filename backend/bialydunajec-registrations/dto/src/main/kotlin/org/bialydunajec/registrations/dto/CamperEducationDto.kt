package org.bialydunajec.registrations.dto


data class CamperEducationDto(
        val university: String,

        val faculty: String,

        val fieldOfStudy: String,

        val highSchool: String?,

        val isHighSchoolRecentGraduate: Boolean
)