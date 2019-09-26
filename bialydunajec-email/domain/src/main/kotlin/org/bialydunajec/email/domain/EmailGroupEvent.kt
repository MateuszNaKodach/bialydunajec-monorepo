package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup

sealed class EmailGroupEvent : DomainEvent<EmailGroupId> {

    class EmailGroupCreated(
            override val aggregateId: EmailGroupId,
            val emailGroup: EmailGroup
    ) : EmailGroupEvent()

}
