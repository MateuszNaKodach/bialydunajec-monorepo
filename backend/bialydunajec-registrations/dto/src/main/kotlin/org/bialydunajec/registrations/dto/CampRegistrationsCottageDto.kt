package org.bialydunajec.registrations.dto

import org.bialydunajec.ddd.dto.sharedkernel.PlaceDto


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
