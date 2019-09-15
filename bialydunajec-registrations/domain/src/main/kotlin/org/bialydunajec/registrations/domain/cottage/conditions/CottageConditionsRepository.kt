package org.bialydunajec.registrations.domain.cottage.conditions

import org.bialydunajec.registrations.domain.cottage.CottageId

interface CottageConditionsRepository {

    fun findByCottageId(cottageId: CottageId): CottageConditions?

    fun save(aggregateRoot: CottageConditions): CottageConditions
}
