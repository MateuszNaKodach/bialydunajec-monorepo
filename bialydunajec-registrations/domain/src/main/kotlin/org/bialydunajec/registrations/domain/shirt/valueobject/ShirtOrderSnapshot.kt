package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
class ShirtOrderSnapshot(
        @Embedded
        val campParticipantId: CampParticipantId,
        @Embedded
        val color: Color,
        @Embedded
        val size: ShirtSize
)