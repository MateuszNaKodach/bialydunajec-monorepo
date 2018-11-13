package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.cottage.Cottage

abstract class CottageFreeSpaceSpecification(
        protected val campParticipantRepository: CampParticipantRepository
) : CompositeSpecification<Cottage>() {

    companion object {
        fun createFor(camperGender: Gender, campParticipantRepository: CampParticipantRepository): Specification<Cottage> {
            return when (camperGender) {
                Gender.MALE -> CottageMaleFreeSpaceSpecification(campParticipantRepository)
                Gender.FEMALE -> CottageFemaleFreeSpaceSpecification(campParticipantRepository)
            }
        }
    }
}