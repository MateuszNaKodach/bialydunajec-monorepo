package org.bialydunajec.registrations.presentation.rest.v1.request

import org.bialydunajec.ddd.application.base.query.api.dto.PlaceDto
import org.bialydunajec.registrations.application.query.api.dto.BankTransferDetailsDto
import org.bialydunajec.registrations.application.query.api.dto.CampersLimitationsDto
import org.bialydunajec.registrations.application.query.api.dto.CottageSpaceDto

data class UpdateCottageRequest(
        val name: String,
        val logoImageUrl: String?,
        val buildingPhotoUrl: String?,
        val place: PlaceDto?,
        val cottageSpace: CottageSpaceDto?,
        val campersLimitations: CampersLimitationsDto?,
        val bankTransferDetails: BankTransferDetailsDto?
)