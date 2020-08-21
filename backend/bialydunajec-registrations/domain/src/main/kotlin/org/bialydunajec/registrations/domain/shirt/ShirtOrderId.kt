package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import javax.persistence.Embeddable

@Embeddable
class ShirtOrderId : AggregateId()