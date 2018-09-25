package org.bialydunajec.registrations.domain.camperapplication

import org.bialydunajec.ddd.domain.event.DomainEvent

sealed class CamperApplicationEvents : DomainEvent<CamperApplicationId> {
    data class CamperApplicationCreated(override val aggregateId: CamperApplicationId):CamperApplicationEvents()
}