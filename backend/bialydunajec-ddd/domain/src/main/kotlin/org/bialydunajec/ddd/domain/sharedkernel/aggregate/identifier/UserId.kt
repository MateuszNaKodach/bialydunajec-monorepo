package org.bialydunajec.ddd.domain.sharedkernel.aggregate.identifier

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class UserId() : AggregateId()
