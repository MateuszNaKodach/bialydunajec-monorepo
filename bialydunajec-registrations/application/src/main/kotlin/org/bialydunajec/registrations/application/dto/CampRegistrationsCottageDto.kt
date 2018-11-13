package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.dto.PlaceDto
import org.bialydunajec.ddd.application.base.dto.toDto
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSnapshot


data class CampRegistrationsCottageDto(
        val cottageId: String,
        val cottageType: String,
        val academicMinistryId: String?,
        val name: String,
        val logoImageUrl: String?,
        val buildingPhotoUrl: String?,
        val place: PlaceDto?,
        val hasSpace: Boolean
) {
    internal companion object {
        fun from(snapshot: CottageSnapshot, hasSpace: Boolean) = CampRegistrationsCottageDto(
                cottageId = snapshot.cottageId.toString(),
                cottageType = snapshot.cottageType.toString(),
                academicMinistryId = snapshot.academicMinistryId.toStringOrNull(),
                name = snapshot.name,
                logoImageUrl = snapshot.logoImageUrl.toStringOrNull(),
                buildingPhotoUrl = snapshot.buildingPhotoUrl.toStringOrNull(),
                place = snapshot.place?.toDto(),
                hasSpace = hasSpace
        )
    }
}
