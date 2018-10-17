package org.bialydunajec.registrations.application.dto

import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipantSnapshot

data class CampParticipantDto(
        val campParticipantId: String,
        val campRegistrationsEditionId: String,
        val confirmedApplication: CamperApplicationDto?,
        val currentCamperData: CamperApplicationDto,
        val stayDuration: StayDurationDto,
        val participationStatus: String
) {

    internal companion object {
        fun from(snapshot: CampParticipantSnapshot) =
                with(snapshot) {
                    CampParticipantDto(
                            campParticipantId.toString(),
                            campRegistrationsEditionId.toString(),
                            confirmedApplication?.toDto(),
                            currentCamperData.toDto(),
                            stayDuration.toDto(),
                            participationStatus.toString()
                    )
                }
    }

}