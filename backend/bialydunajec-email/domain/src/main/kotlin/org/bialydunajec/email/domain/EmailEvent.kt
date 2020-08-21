package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

sealed class EmailEvent : DomainEvent<EmailId> {

    class EmailCatalogized(
        override val aggregateId: EmailId,
        val emailGroupId: EmailGroupId,
        val emailAddress: EmailAddress,
        val emailOwner: EmailAddressOwner
    ) : EmailEvent()

    class EmailOwnerCorrected(
        override val aggregateId: EmailId,
        val emailGroupId: EmailGroupId,
        val emailAddress: EmailAddress,
        val emailOwner: EmailAddressOwner
    ) : EmailEvent()

    class EmailAddressChanged(
        override val aggregateId: EmailId,
        val emailGroupId: EmailGroupId,
        val oldEmailAddress: EmailAddress,
        val newEmailAddress: EmailAddress,
        val newEmailId: EmailId,
        val emailOwner: EmailAddressOwner
    ) : EmailEvent()
}


