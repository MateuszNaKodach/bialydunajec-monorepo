package org.bialydunajec.academicministry.application.dto

import org.bialydunajec.academicministry.domain.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.ddd.application.base.dto.toDto
import org.bialydunajec.ddd.dto.sharedkernel.ExtendedDescriptionDto
import org.bialydunajec.ddd.dto.sharedkernel.PlaceDto
import org.bialydunajec.ddd.dto.sharedkernel.SocialMediaDto
import org.bialydunajec.ddd.domain.extensions.toStringOrNull

data class AcademicMinistryDto(
        val academicMinistryId: String,
        val officialName: String,
        val shortName: String?,
        val logoImageUrl: String?,
        val place: PlaceDto?,
        val socialMedia: SocialMediaDto?,
        val emailAddress: String?,
        val photoUrl: String?,
        val description: ExtendedDescriptionDto?
) {
    companion object {
        fun from(snapshot: AcademicMinistrySnapshot) =
                AcademicMinistryDto(
                        academicMinistryId = snapshot.academicMinistryId.toString(),
                        officialName = snapshot.officialName,
                        shortName = snapshot.shortName,
                        logoImageUrl = snapshot.logoImageUrl.toStringOrNull(),
                        place = snapshot.place?.toDto(),
                        socialMedia = snapshot.socialMedia?.toDto(),
                        emailAddress = snapshot.emailAddress.toStringOrNull(),
                        photoUrl = snapshot.photoUrl.toStringOrNull(),
                        description = snapshot.description?.toDto()
                )
    }

    fun getDisplayName() = shortName ?: officialName
}
