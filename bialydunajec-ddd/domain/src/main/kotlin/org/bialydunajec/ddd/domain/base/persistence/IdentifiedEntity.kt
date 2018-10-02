package org.bialydunajec.ddd.domain.base.persistence

import org.bialydunajec.ddd.domain.base.valueobject.EntityId

interface IdentifiedEntity<EntityIdType : EntityId> {
    val entityId: EntityIdType
}