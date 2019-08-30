package org.bialydunajec.registrations.domain.cottage.conditions

import org.bialydunajec.registrations.domain.cottage.CottageId

interface CottageConditionsRepository { // todo: Provide real implementation

    fun findByCottageId(cottageId: CottageId): CottageConditions?

    fun save(conditions: CottageConditions)
}
