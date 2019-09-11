package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class EmailGroupEvent : DomainEvent<EmailGroupId> {

    class EmailGroupCreated(
            override val aggregateId: EmailGroupId,
            val name: String
    ) : EmailGroupEvent()

}
