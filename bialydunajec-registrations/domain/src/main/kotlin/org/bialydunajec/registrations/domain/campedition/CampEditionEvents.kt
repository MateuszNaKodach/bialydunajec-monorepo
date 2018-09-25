package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.event.DomainEvent


sealed class CampEditionEvents : DomainEvent<CampEditionId> {
    data class CampEditionCreated(override val aggregateId: CampEditionId) : CampEditionEvents()
}