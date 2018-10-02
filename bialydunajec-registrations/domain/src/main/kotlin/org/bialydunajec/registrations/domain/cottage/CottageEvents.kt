package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class CottageEvents : DomainEvent<CottageId> {
    data class CottageCreated(override val aggregateId: CottageId): CottageEvents()
}