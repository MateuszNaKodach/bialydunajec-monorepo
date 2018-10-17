package org.bialydunajec.registrations.domain.camper.valueobject

import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId

data class CampParticipantRegistrationSnapshot(
        val campParticipantRegistrationId: CampParticipantRegistrationId,
        val campParticipantId: CampParticipantId,
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val originalCamperApplication: CamperApplication,
        val camperApplication: CamperApplication,
        val status: RegistrationStatus,
        val verificationCode: String
)