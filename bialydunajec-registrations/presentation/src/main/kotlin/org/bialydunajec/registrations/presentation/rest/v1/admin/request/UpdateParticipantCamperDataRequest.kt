package org.bialydunajec.registrations.presentation.rest.v1.admin.request

import org.bialydunajec.ddd.base.dto.AddressDto
import org.bialydunajec.registrations.dto.CamperEducationDto
import org.bialydunajec.registrations.dto.CamperPersonalDataDto

data class CorrectCampParticipantCamperDataRequest(
        val cottageId: String,
        val personalData: CamperPersonalDataDto,
        val homeAddress: AddressDto,
        val phoneNumber: String,
        val emailAddress: String,
        val camperEducation: CamperEducationDto,
        val statisticalAnswers: StatisticalAnswersDto? = null,
        val meanOfTransport: String? = null
)

data class CamperShirtOrderDto(
        val shirtColorOptionId: String,
        val shirtSizeOptionId: String
)

data class StatisticalAnswersDto(
        val knowAboutCampFrom: String? = null,
        val onCampForTime: Int? = null
)