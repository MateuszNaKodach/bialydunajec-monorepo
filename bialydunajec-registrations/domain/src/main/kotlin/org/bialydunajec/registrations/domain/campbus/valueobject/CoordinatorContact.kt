package org.bialydunajec.registrations.domain.campbus.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Lob

@Embeddable
internal class CoordinatorContact(
        @Embedded
        val emailAddress: EmailAddress,
        @Embedded
        val phoneNumber: PhoneNumber,
        @Lob
        val notes: String? = null
)