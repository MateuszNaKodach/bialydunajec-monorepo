package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSnapshot
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageStatus

sealed class CottageEvents : DomainEvent<CottageId> {
    data class CottageCreated(
            override val aggregateId: CottageId,
            val snapshot: CottageSnapshot
    ): CottageEvents()

    data class CottageUpdated(
            override val aggregateId: CottageId,
            val snapshot: CottageSnapshot
    ): CottageEvents()

    data class CottageUnconfigured(
        override val aggregateId: CottageId,
        val snapshot: CottageSnapshot
    ): CottageEvents()

    data class CottageConfigured(
        override val aggregateId: CottageId,
        val snapshot: CottageSnapshot
    ): CottageEvents()

    data class CottageDeleted(
            override val aggregateId: CottageId,
            val snapshot: CottageSnapshot
    ): CottageEvents()
}
