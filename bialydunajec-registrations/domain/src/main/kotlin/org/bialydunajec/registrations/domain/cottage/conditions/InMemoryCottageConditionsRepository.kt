package org.bialydunajec.registrations.domain.cottage.conditions

import org.bialydunajec.registrations.domain.cottage.CottageId

class InMemoryCottageConditionsRepository : CottageConditionsRepository {

    private var item: CottageConditions? = null

    override fun save(conditions: CottageConditions): CottageConditions {
        item = conditions
        return conditions
    }

    override fun findByCottageId(cottageId: CottageId): CottageConditions? {
        return item
    }
}
