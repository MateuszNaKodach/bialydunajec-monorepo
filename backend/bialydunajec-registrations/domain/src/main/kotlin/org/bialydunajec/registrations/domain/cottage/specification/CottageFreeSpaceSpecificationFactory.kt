package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.springframework.stereotype.Component

class CottageFreeSpaceSpecificationFactory (
        private val campParticipantRepository: CampParticipantRepository
) {

    fun createFor(camperApplication: CamperApplication) =
            CottageFreeSpaceSpecification.createFor(camperApplication.personalData.gender, campParticipantRepository)

    fun createFor(camperGender: Gender) =
            CottageFreeSpaceSpecification.createFor(camperGender, campParticipantRepository)

}
