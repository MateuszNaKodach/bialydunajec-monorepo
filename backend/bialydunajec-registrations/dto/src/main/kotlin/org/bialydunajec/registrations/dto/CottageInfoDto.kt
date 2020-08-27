package org.bialydunajec.registrations.dto

import org.bialydunajec.ddd.dto.sharedkernel.AuditDto
import org.bialydunajec.ddd.dto.sharedkernel.PlaceDto


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
    companion object {
    }
}

