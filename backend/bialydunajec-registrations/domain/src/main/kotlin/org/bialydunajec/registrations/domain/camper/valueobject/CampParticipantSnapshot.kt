package org.bialydunajec.registrations.domain.camper.valueobject

import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId

class CampParticipantSnapshot internal constructor(
        val campParticipantId: CampParticipantId,
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val confirmedApplication: CamperApplication?,
        val currentCamperData: CamperApplication,
        val stayDuration: StayDuration,
        val participationStatus: ParticipationStatus
)