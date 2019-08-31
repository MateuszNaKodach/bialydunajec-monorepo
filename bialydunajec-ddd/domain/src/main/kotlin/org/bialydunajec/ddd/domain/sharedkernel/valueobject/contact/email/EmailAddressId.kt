package org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId

class EmailAddressId (emailAddressId: String = defaultValue()) : AggregateId(emailAddressId)
