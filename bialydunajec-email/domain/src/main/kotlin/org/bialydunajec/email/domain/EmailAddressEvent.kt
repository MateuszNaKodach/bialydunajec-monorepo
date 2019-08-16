package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class EmailAddressEvent: DomainEvent<EmailAddressId> {

}
