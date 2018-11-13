package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.ddd.domain.base.specification.CompositeSpecification
import org.bialydunajec.registrations.domain.cottage.Cottage

class ActivatedCottageSpecification : CompositeSpecification<Cottage>() {

    override fun isSatisfiedBy(candidate: Cottage) =
            candidate.isActivated()
}