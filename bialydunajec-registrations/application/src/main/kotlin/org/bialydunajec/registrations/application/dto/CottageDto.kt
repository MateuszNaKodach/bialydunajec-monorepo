package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.query.api.dto.PlaceDto
import org.bialydunajec.ddd.application.base.query.api.dto.toDto
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
    internal companion object {
        fun from(snapshot: CottageSnapshot) = CottageDto(
                cottageId = snapshot.cottageId.toString(),
                campRegistrationsEditionId = snapshot.campRegistrationsEditionId.toString(),
                cottageType = snapshot.cottageType.toString(),
                academicMinistryId = snapshot.academicMinistryId.toStringOrNull(),
                name = snapshot.name,
                logoImageUrl = snapshot.logoImageUrl.toStringOrNull(),
                buildingPhotoUrl = snapshot.buildingPhotoUrl.toStringOrNull(),
                place = snapshot.place?.toDto(),
                cottageSpace = snapshot.cottageSpace?.toDto(),
                campersLimitations = snapshot.campersLimitations?.toDto(),
                bankTransferDetails = snapshot.bankTransferDetails?.toDto(),
                cottageState = snapshot.cottageState.toString(),
                cottageBoss = snapshot.cottageBoss?.toDto()
        )
    }
}

