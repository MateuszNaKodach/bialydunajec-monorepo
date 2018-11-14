package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtId
import javax.persistence.*

@Embeddable
class ShirtOrderSnapshot(
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "aggregateId", column = Column(name = "shirtOrder_campEditionShirtId"))
        )
        val campEditionShirtId: CampEditionShirtId,
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "aggregateId", column = Column(name = "shirtOrder_campParticipantId"))
        )
        val campParticipantId: CampParticipantId,
        @Embedded
        val color: Color,
        @Embedded
        val size: ShirtSize
)