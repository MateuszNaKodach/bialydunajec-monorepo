package org.bialydunajec.registrations.domain.campedition.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.registrations.domain.campedition.CampEdition
import java.time.ZonedDateTime

class InProgressCampRegistrationsSpecification : CompositeSpecification<CampEdition>() {

    override fun isSatisfiedBy(candidate: CampEdition) =
            candidate.campRegistrationsInProgress()
}