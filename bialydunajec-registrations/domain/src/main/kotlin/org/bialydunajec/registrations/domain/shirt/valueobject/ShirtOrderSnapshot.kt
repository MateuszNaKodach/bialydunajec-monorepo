package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId

class ShirtOrderSnapshot(
        val campParticipantId: CampParticipantId,
        val color: Color,
        val size: ShirtSize
)