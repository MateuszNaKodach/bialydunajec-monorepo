package org.bialydunajec.ddd.domain.sharedkernel.aggregate.identifier

import org.bialydunajec.ddd.domain.base.valueobject.DbAggregateId
import javax.persistence.Embeddable

@Embeddable
class UserId() : DbAggregateId()
