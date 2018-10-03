package org.bialydunajec.registrations.domain.campedition.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.cottage.CottageRepository


class CampRegistrationsHasMinimumCottagesToStartSpecification(
        private val cottagesRepository: CottageRepository
) : CompositeSpecification<CampRegistrationsEdition>() {

    //TODO: One cottage with free space for camper!
    override fun isSatisfiedBy(candidate: CampRegistrationsEdition) = cottagesRepository.countByCampEditionId(candidate.getAggregateId()) >= 1
}