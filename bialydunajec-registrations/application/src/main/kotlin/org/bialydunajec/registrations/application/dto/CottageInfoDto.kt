package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.dto.AuditDto
import org.bialydunajec.ddd.application.base.dto.PlaceDto
import org.bialydunajec.ddd.application.base.dto.toDto
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSnapshot


data class CottageInfoDto(
        val cottageId: String,
        val campRegistrationsEditionId: String,
        val cottageType: String,
        val academicMinistryId: String?,
        val name: String,
        val logoImageUrl: String?,
        val buildingPhotoUrl: String?,
        val place: PlaceDto?,
        val cottageState: String,
        val cottageBoss: CottageBossDto?,
        val audit: AuditDto
) {
    internal companion object {
        fun from(snapshot: CottageSnapshot) = CottageInfoDto(
                cottageId = snapshot.cottageId.toString(),
                campRegistrationsEditionId = snapshot.campRegistrationsEditionId.toString(),
                cottageType = snapshot.cottageType.toString(),
                academicMinistryId = snapshot.academicMinistryId.toStringOrNull(),
                name = snapshot.name,
                logoImageUrl = snapshot.logoImageUrl.toStringOrNull(),
                buildingPhotoUrl = snapshot.buildingPhotoUrl.toStringOrNull(),
                place = snapshot.place?.toDto(),
                cottageState = snapshot.cottageState.toString(),
                cottageBoss = snapshot.cottageBoss?.toDto(),
                audit = snapshot.audit.toDto()
        )
    }
}

