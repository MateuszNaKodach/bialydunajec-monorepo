package org.bialydunajec.registrations.application.dto

import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipantSnapshot
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSnapshot

data class CampParticipantDto(
        val campParticipantId: String,
        val campRegistrationsEditionId: String,
        val confirmedApplication: CamperApplicationWithCottageDto?,
        val currentCamperData: CamperApplicationWithCottageDto,
        val stayDuration: StayDurationDto,
        val participationStatus: String
) {

    internal companion object {
        fun from(snapshot: CampParticipantSnapshot,
                 confirmedCottage: CottageSnapshot?,
                 currentCottage: CottageSnapshot) =
                with(snapshot) {
                    CampParticipantDto(
                            campParticipantId.toString(),
                            campRegistrationsEditionId.toString(),
                            confirmedCottage?.let { confirmedApplication?.toDtoWithCottage(confirmedCottage)},
                            currentCamperData.toDtoWithCottage(currentCottage),
                            stayDuration.toDto(),
                            participationStatus.toString()
                    )
                }
    }

}