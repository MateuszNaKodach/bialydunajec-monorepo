package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.dto.AddressDto

data class CamperApplicationDto(
        val cottageId: String,
        val personalData: CamperPersonalDataDto,
        val homeAddress: AddressDto,
        val phoneNumber: String,
        val emailAddress: String,
        val camperEducation: CamperEducationDto
)


data class CamperApplicationWithCottageDto(
        val cottage: CamperApplicationCottageDto,
        val personalData: CamperPersonalDataDto,
        val homeAddress: AddressDto,
        val phoneNumber: String,
        val emailAddress: String,
        val camperEducation: CamperEducationDto
)

data class CamperApplicationCottageDto(
        val cottageId: String,
        val cottageName: String
)