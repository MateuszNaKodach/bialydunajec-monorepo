package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class EmailGroupId(emailGroupId: String = defaultValue()) : AggregateId(emailGroupId)
