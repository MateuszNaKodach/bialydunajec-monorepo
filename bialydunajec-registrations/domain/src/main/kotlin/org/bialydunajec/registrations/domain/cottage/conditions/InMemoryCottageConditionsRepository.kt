package org.bialydunajec.registrations.domain.cottage.conditions

import org.bialydunajec.registrations.domain.cottage.CottageId
import org.springframework.stereotype.Repository

@Repository
class InMemoryCottageConditionsRepository : CottageConditionsRepository {

    private var item: CottageConditions? = null

    override fun save(conditions: CottageConditions) {
        item = conditions
    }

    override fun findByCottageId(cottageId: CottageId): CottageConditions? {
        return item
    }
}
