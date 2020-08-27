package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class ShirtOrderId : AggregateId()
