package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.cottage.Cottage

internal class CottageMaleFreeSpaceSpecification internal constructor(
        campParticipantRepository: CampParticipantRepository
) : CottageFreeSpaceSpecification(campParticipantRepository) {

    override fun isSatisfiedBy(candidate: Cottage): Boolean {
        val campParticipantsFromCottage = campParticipantRepository.findAllByCottageId(candidate.getAggregateId())
        val allParticipantsCount = campParticipantsFromCottage.size
        val maleParticipantsCount = campParticipantsFromCottage.filter { it.getPersonalData().gender.isMale }.size
        return allParticipantsCount < candidate.getCottageSpace().getSpaceForCampersRegistrations()
                && maleParticipantsCount < candidate.getCottageSpace().maxMaleTotal ?: Int.MAX_VALUE
    }

}