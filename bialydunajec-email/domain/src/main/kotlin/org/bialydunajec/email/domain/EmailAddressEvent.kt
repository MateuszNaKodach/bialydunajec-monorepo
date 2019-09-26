package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import java.time.ZonedDateTime

sealed class EmailAddressEvent : DomainEvent<EmailAddressId> {

    class EmailAddressCreated(
            override val aggregateId: EmailAddressId,
            val emailAddress: EmailAddress,
            val isActive: Boolean
    ) : EmailAddressEvent()

    class EmailAddressCatalogizedToEmailGroup(
            override val aggregateId: EmailAddressId,
            val emailAddress: EmailAddress,
            val previousEmailAddressId: EmailAddressId,
            val newEmailGroupId: EmailGroupId,
            val newEmailGroup: EmailAddressGroup,
            val emailAddressOwner: EmailAddressOwner
    ) : EmailAddressEvent()

    class EmailAddressDeactivated(
            override val aggregateId: EmailAddressId,
            val emailAddress: EmailAddress,
            val emailGroupId: EmailGroupId?
    ) : EmailAddressEvent()

    class EmailAddressUpdated(
            override val aggregateId: EmailAddressId,
            val newEmailAddress: EmailAddress,
            val previousEmailAddressId: EmailAddressId
    ) : EmailAddressEvent()

    class EmailAddressBelongingToGroupUpdated (
        override val aggregateId: EmailAddressId,
        val newEmailAddress: EmailAddress,
        val previousEmailAddressId: EmailAddressId,
        val emailGroupId: EmailGroupId,
        val emailAddressGroup: EmailAddressGroup,
        val emailOwner: EmailAddressOwner?
    ): EmailAddressEvent()
}


