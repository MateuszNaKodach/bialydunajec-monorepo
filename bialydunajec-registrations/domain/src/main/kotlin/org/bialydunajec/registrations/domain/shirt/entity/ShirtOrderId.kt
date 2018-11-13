package org.bialydunajec.registrations.domain.shirt.entity

import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import javax.persistence.Embeddable

@Embeddable
class ShirtOrderId : EntityId()