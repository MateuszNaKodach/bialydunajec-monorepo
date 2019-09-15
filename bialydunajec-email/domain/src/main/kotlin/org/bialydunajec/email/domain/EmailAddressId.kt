package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup


const val DEFAULT_EMAIL_ADDRESS_GROUP: String = "DEFAULT_GROUP";

class EmailAddressId private constructor(emailAddressId: String) : AggregateId(emailAddressId) {

    companion object {
        @JvmStatic
        fun from(emailAddress: EmailAddress, emailAddressGroup: EmailAddressGroup) =
                EmailAddressId(createEmailAddressIdFrom(emailAddress.toString(), emailAddressGroup.name))

        @JvmStatic
        fun from(emailAddress: EmailAddress) =
                EmailAddressId(createEmailAddressIdFrom(emailAddress.toString(), DEFAULT_EMAIL_ADDRESS_GROUP))

        @JvmStatic
        fun from(emailAddressId: String) = EmailAddressId(emailAddressId)

        private fun createEmailAddressIdFrom(emailAddress: String, emailAddressGroup: String) = "$emailAddress-$emailAddressGroup"
    }

}