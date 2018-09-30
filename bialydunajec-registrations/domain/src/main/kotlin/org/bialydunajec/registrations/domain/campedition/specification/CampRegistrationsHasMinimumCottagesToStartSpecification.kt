package org.bialydunajec.registrations.domain.campedition.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.registrations.domain.campedition.CampEdition
import org.bialydunajec.registrations.domain.cottage.CottageRepository


class CampRegistrationsHasMinimumCottagesToStartSpecification(
        private val cottagesRepository: CottageRepository
) : CompositeSpecification<CampEdition>() {

    //TODO: One cottage with free space for camper!
    override fun isSatisfiedBy(candidate: CampEdition) = cottagesRepository.count() >= 1
}