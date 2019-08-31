package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId

class EmailAddressId(emailAddressId: String = defaultValue()) : AggregateId(emailAddressId)
