package org.bialydunajec.registrations.domain.campregistrations.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrations

class InProgressCampRegistrationsSpecification : CompositeSpecification<CampRegistrations>() {

    override fun isSatisfiedBy(candidate: CampRegistrations) =
            candidate.isStarted() && !candidate.isEnded()
}