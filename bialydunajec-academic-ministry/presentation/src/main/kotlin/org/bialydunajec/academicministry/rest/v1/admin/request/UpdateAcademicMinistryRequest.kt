package org.bialydunajec.academicministry.rest.v1.admin.request

import org.bialydunajec.ddd.base.dto.ExtendedDescriptionDto
import org.bialydunajec.ddd.base.dto.PlaceDto
import org.bialydunajec.ddd.base.dto.SocialMediaDto


internal data class UpdateAcademicMinistryRequest (
        val officialName: String,
        val shortName: String?,
        val logoImageUrl: String?,
        val place: PlaceDto?,
        val socialMedia: SocialMediaDto,
        val emailAddress: String?,
        val photoUrl: String?,
        val description: ExtendedDescriptionDto?
)