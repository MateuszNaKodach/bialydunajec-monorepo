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
            val newEmailGroupId: EmailGroupId,
            val newEmailGroup: EmailAddressGroup,
            val emailAddressOwner: EmailAddressOwner
    ) : EmailAddressEvent()

    class EmailAddressDeactivated(
            override val aggregateId: EmailAddressId,
            val emailAddress: EmailAddress
    ) : EmailAddressEvent()

}
