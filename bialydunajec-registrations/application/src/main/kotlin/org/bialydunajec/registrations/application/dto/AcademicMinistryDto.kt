package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.registrations.domain.academicministry.valueobject.AcademicMinistrySnapshot


data class AcademicMinistryDto(
        val academicMinistryId: String,
        val officialName: String,
        val shortName: String?,
        val logoImageUrl: String?
) {
    val displayName = shortName ?: officialName

    internal companion object {
        fun from(snapshot: AcademicMinistrySnapshot) = AcademicMinistryDto(
                academicMinistryId = snapshot.academicMinistryId.toString(),
                officialName = snapshot.officialName,
                shortName = snapshot.shortName,
                logoImageUrl = snapshot.logoImageUrl.toStringOrNull()
        )
    }

}