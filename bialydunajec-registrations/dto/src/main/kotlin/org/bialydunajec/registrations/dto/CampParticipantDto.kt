package org.bialydunajec.registrations.dto

data class CampParticipantDto(
        val campParticipantId: String,
        val campRegistrationsEditionId: String,
        val confirmedApplication: CamperApplicationWithCottageDto?,
        val currentCamperData: CamperApplicationWithCottageDto,
        val stayDuration: StayDurationDto,
        val participationStatus: String
) {

    companion object {
    }

}