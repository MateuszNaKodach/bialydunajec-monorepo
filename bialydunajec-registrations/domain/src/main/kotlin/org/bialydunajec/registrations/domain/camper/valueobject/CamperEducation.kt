package org.bialydunajec.registrations.domain.camper.valueobject

import javax.persistence.Embeddable

@Embeddable
data class CamperEducation(
        val university: String,

        val faculty: String,

        val fieldOfStudy: String,

        val highSchool: String?,

        val isHighSchoolRecentGraduate: Boolean
)
