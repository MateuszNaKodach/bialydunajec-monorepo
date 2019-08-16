package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId

class EmailGroupId (emailGroupId: String = defaultValue()) : AggregateId(emailGroupId)
