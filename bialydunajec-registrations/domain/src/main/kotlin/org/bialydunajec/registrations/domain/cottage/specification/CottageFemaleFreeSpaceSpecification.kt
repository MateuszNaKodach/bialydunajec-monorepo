package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.registrations.domain.camper.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.Cottage

internal class CottageFemaleFreeSpaceSpecification internal constructor(
        campParticipantRepository: CampParticipantRepository
): CottageFreeSpaceSpecification(campParticipantRepository) {

    override fun isSatisfiedBy(candidate: Cottage): Boolean {
        val campParticipantsFromCottage = campParticipantRepository.findAllByCottageId(candidate.getAggregateId())
        val allParticipantsCount = campParticipantsFromCottage.size
        val femaleParticipantsCount = campParticipantsFromCottage.filter { it.getPersonalData().gender.isFemale }.size
        return allParticipantsCount < candidate.getCottageSpace().getSpaceForCampersRegistrations()
                && femaleParticipantsCount < candidate.getCottageSpace().maxFemaleTotal ?: Int.MAX_VALUE
    }

}