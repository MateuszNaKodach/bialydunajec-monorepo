package org.bialydunajec.registrations.domain.cottage.specification

import org.bialydunajec.registrations.domain.cottage.Cottage

class CottageMaleFreeSpaceSpecification : CottageFreeSpaceSpecification() {
    override fun isSatisfiedBy(candidate: Cottage) = false
}