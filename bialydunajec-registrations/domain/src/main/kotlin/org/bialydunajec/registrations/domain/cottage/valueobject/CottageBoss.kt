package org.bialydunajec.registrations.domain.cottage.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
data class CottageBoss(
        @Embedded
        val firstName: FirstName,

        @Embedded
        val lastName: LastName,

        @Embedded
        val phoneNumber: PhoneNumber?,

        @Embedded
        val emailAddress: EmailAddress?,

        val university: String?,

        val fieldOfStudy: String?,

        @Embedded
        val photoUrl: Url?,

        @Embedded
        val personalDescription: ExtendedDescription?
)