package org.bialydunajec.registrations.presentation.rest.v1.request

import org.bialydunajec.ddd.application.base.dto.AddressDto
import org.bialydunajec.registrations.application.dto.CamperEducationDto
import org.bialydunajec.registrations.application.dto.CamperPersonalDataDto

data class CampParticipantRegistrationRequest(
        val cottageId: String,
        val personalData: CamperPersonalDataDto,
        val homeAddress: AddressDto,
        val phoneNumber: String,
        val emailAddress: String,
        val camperEducation: CamperEducationDto
)