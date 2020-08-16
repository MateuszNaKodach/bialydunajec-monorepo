package org.bialydunajec.registrations.domain.campedition.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository


class CampRegistrationsCanStartSpecification(
        private val campRegistrationsEditionRepository: CampRegistrationsEditionRepository
) : CompositeSpecification<CampRegistrationsEdition>() {

    override fun isSatisfiedBy(candidate: CampRegistrationsEdition) =
            campRegistrationsEditionRepository.findAllBySpecification(InProgressCampRegistrationsSpecification()).isEmpty();
}