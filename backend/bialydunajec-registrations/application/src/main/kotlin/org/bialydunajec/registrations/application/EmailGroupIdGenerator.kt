package org.bialydunajec.registrations.application

import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.Cottage

private const val CAMP_PARTICIPANT = "CAMP-PARTICIPANT"
private const val EDITION = "EDITION"
private const val COTTAGE = "COTTAGE"


object EmailGroupIdGenerator {
    fun campParticipantRegisteredInCottage(cottage: Cottage) =
        "${CAMP_PARTICIPANT}+${EDITION}_${cottage.getCampEditionId()}+${COTTAGE}_${cottage.getAggregateId()}"

    fun campParticipantRegisteredOnCampEdition(campRegistrationsEditionId: CampRegistrationsEditionId) =
        "${CAMP_PARTICIPANT}+${EDITION}_${campRegistrationsEditionId}"
}
