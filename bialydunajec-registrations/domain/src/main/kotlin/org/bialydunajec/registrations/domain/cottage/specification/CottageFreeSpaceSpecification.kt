package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.camper.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.Cottage

abstract class CottageFreeSpaceSpecification(
        private val campParticipantRepository: CampParticipantRepository
) : CompositeSpecification<Cottage>() {
    companion object {
        fun createFor(camperApplication: CamperApplication, campParticipantRepository: CampParticipantRepository): Specification<Cottage> {
            return when (camperApplication.personalData.gender) {
                Gender.MALE -> CottageMaleFreeSpaceSpecification(campParticipantRepository, camperApplication)
                Gender.FEMALE -> CottageFemaleFreeSpaceSpecification(campParticipantRepository, camperApplication)
            }
        }
    }
}