package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.registrations.domain.camper.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.springframework.stereotype.Component

@Component
class CottageFreeSpaceSpecificationFactory (
        private val campParticipantRepository: CampParticipantRepository
) {

    fun createFor(camperApplication: CamperApplication) =
            CottageFreeSpaceSpecification.createFor(camperApplication, campParticipantRepository)

}