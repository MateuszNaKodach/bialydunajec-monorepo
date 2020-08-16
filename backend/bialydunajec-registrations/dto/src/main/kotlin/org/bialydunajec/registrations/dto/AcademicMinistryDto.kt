package org.bialydunajec.registrations.dto


data class AcademicMinistryDto(
        val academicMinistryId: String,
        val officialName: String,
        val shortName: String?,
        val logoImageUrl: String?
) {
    val displayName = shortName ?: officialName
}