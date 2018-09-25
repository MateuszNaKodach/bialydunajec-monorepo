package org.bialydunajec.registrations.domain.cottageaccommodation.entity.camper

import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import javax.persistence.Embeddable

@Embeddable
class CamperId(entityId: String? = null) : EntityId(entityId ?: EntityId.defaultValue())