package org.bialydunajec.registrations.domain.campedition.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition

class InProgressCampRegistrationsSpecification : CompositeSpecification<CampRegistrationsEdition>() {

    override fun isSatisfiedBy(candidate: CampRegistrationsEdition) =
            candidate.campRegistrationsInProgress()
}