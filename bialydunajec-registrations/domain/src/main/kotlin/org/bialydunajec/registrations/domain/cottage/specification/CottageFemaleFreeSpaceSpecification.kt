package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.registrations.domain.camper.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.Cottage

internal class CottageFemaleFreeSpaceSpecification internal constructor(
        campParticipantRepository: CampParticipantRepository,
        private val camperApplication: CamperApplication
): CottageFreeSpaceSpecification(campParticipantRepository) {

    override fun isSatisfiedBy(candidate: Cottage): Boolean {
        return true;
    }

}