package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import java.time.ZonedDateTime

sealed class EmailAddressEvent : DomainEvent<EmailAddressId> {

    class EmailAddressCreated(
            override val aggregateId: EmailAddressId,
            val emailAddressValue: EmailAddress
    ) : EmailAddressEvent()

    class EmailAddressUpdated(
            override val aggregateId: EmailAddressId,
            val emailAddressValue: EmailAddress
    ) : EmailAddressEvent()

    class EmailAddressAddedToEmailGroup(
            override val aggregateId: EmailAddressId,
            val newEmailGroupId: EmailGroupId
    ) : EmailAddressEvent()

}
