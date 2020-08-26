package org.bialydunajec.ddd.domain.base.persistence

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.EntityId

interface IdentifiedEntity<EntityIdType : EntityId> {
    val entityId: EntityIdType
}
