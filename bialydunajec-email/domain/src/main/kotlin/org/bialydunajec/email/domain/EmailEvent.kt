package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

sealed class EmailEvent : DomainEvent<EmailId> {

    class EmailCatalogized(
        override val aggregateId: EmailId,
        val emailAddress: EmailAddress,
        val emailGroupId: EmailGroupId,
        val emailOwner: EmailAddressOwner
    ) : EmailEvent()

    class EmailOwnerCorrected(
        override val aggregateId: EmailId,
        val emailAddress: EmailAddress,
        val emailOwner: EmailAddressOwner
    ) : EmailEvent()

    class EmailAddressChanged(
        override val aggregateId: EmailId,
        val newEmailAddress: EmailAddress,
        val newEmailId: EmailId
    ) : EmailEvent()
}


