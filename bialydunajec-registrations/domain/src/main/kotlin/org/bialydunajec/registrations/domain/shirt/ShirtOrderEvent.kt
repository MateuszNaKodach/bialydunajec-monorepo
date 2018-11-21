package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtOrderSnapshot

sealed class ShirtOrderEvent(
        override val aggregateId: ShirtOrderId
) : DomainEvent<ShirtOrderId> {

    class OrderPlaced(
            aggregateId: ShirtOrderId,
            val snapshot: ShirtOrderSnapshot
    ) : ShirtOrderEvent(aggregateId)
}