package org.bialydunajec.registrations.dto

import org.bialydunajec.ddd.dto.sharedkernel.PlaceDto

data class CottageDto(
        val cottageId: String,
        val campRegistrationsEditionId: String,
        val cottageType: String,
        val academicMinistryId: String?,
        val name: String,
        val logoImageUrl: String?,
        val buildingPhotoUrl: String?,
        val place: PlaceDto?,
        val cottageSpace: CottageSpaceDto?,
        val campersLimitations: CampersLimitationsDto?,
        val bankTransferDetails: BankTransferDetailsDto?,
        val cottageState: String,
        val cottageBoss: CottageBossDto?
) {
    companion object {

    }
}

