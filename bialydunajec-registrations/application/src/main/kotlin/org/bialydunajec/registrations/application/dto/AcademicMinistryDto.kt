package org.bialydunajec.registrations.application.dto


data class AcademicMinistryDto(
        val academicMinistryId: String,
        val officialName: String,
        val shortName: String?,
        val logoImageUrl: String?
) {
    val displayName = shortName ?: officialName
}