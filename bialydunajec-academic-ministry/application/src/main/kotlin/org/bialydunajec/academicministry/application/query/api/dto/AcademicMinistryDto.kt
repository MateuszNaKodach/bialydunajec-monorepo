package org.bialydunajec.academicministry.application.query.api.dto

import org.bialydunajec.academicministry.domain.academicministry.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.ddd.application.base.query.api.dto.ExtendedDescriptionDto
import org.bialydunajec.ddd.application.base.query.api.dto.PlaceDto
import org.bialydunajec.ddd.application.base.query.api.dto.SocialMediaDto
import org.bialydunajec.ddd.application.base.query.api.dto.toDto

data class AcademicMinistryDto(
        val academicMinistryId: String,
        val officialName: String,
        val shortName: String,
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
                        logoImageUrl = snapshot.logoImageUrl.toString(),
                        place = snapshot.place?.toDto(),
                        socialMedia = snapshot.socialMedia.toDto(),
                        emailAddress = snapshot.emailAddress.toString(),
                        photoUrl = snapshot.photoUrl.toString(),
                        description = snapshot.description?.toDto()
                )
    }
}
