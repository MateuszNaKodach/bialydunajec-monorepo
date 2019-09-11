package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
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

    class EmailAddressCatalogizedToEmailGroup(
            override val aggregateId: EmailAddressId,
            val newEmailGroupId: EmailGroupId,
            val ownerFirstName: FirstName,
            val ownerLastName: LastName
    ) : EmailAddressEvent()

}
