package org.bialydunajec.registrations.application.query.api.dto

data class CottageSpaceDto(
        val fullCapacity: Int,
        val reservations: Int,
        val maxFemaleTotal: Int?,
        val maxMaleTotal: Int?,
        val highSchoolRecentGraduatesCapacity: Int?,
        val maxFemaleHighSchoolRecentGraduates: Int?,
        val maxMaleHighSchoolRecentGraduates: Int?
)
