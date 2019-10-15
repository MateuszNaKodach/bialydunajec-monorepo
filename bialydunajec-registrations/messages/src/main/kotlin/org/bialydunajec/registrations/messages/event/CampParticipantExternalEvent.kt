package org.bialydunajec.registrations.messages.event

import org.bialydunajec.registrations.dto.CampParticipantDto
import org.bialydunajec.registrations.dto.CamperApplicationWithCottageDto


sealed class CampParticipantExternalEvent {

    data class CampParticipantRegistered(
        val campParticipantId: String,
        val campParticipantRegistrationId: String?,
        val snapshot: CampParticipantDto
    ) : CampParticipantExternalEvent()

    data class CampParticipantConfirmed(
            val campParticipantId: String,
            val snapshot: CampParticipantDto
    ) : CampParticipantExternalEvent()

    data class CampParticipantDataCorrected(
            val campParticipantId: String,
            val campRegistrationsEditionId: String,
            val cottageId: String,
            val oldCamperData: CamperApplicationWithCottageDto,
            val newCamperData: CamperApplicationWithCottageDto
    ) : CampParticipantExternalEvent()


    data class CampParticipantUnregisteredByAuthorized(
            val campParticipantId: String,
            val snapshot: CampParticipantDto
    ) : CampParticipantExternalEvent()
}
