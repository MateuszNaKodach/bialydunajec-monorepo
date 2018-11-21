package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.dto.PlaceDto


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
    companion object {
    }
}
