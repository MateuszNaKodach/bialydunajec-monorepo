package org.bialydunajec.registrations.dto

import org.bialydunajec.ddd.dto.sharedkernel.AddressDto


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
