package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import javax.persistence.*

@Entity
@Table(schema = "email_address")
class EmailAddress (
    emailAddressId: EmailAddressId,

    @Embedded
    private var emailAddress: EmailAddress
): AuditableAggregateRoot<EmailAddressId, EmailAddressEvent>(emailAddressId), Versioned {
    constructor(emailAddressId: EmailAddressId, emailAddress: String) : this(emailAddressId, EmailAddress(emailAddress))

    @Version
    private var version: Long? = null

    override fun getVersion() = version

    fun getEmailAddress() = emailAddress

    fun updateAddress(newEmailAddress: String){
        emailAddress = EmailAddress(newEmailAddress)
    }
}