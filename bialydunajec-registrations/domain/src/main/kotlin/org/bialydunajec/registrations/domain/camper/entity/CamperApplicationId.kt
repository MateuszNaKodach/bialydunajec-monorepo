package org.bialydunajec.registrations.domain.camper.entity

import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.Embeddable

@Embeddable
class CamperApplicationId() : EntityId()