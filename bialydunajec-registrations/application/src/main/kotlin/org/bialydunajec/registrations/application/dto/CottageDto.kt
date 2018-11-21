package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.dto.PlaceDto
import org.bialydunajec.ddd.application.base.dto.toDto
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSnapshot


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

