package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress


class EmailId private constructor(emailAddressId: String) : AggregateId(emailAddressId) {

    companion object {
        @JvmStatic
        fun from(emailAddress: EmailAddress, emailGroupId: EmailGroupId? = EmailGroupId()) =
                EmailId("$emailGroupId-$emailAddress")
    }

}
