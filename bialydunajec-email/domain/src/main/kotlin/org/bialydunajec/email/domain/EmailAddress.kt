package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddressId
import javax.persistence.*

@Entity
@Table(schema = "email_address")
class EmailAddress (
    val emailAddressId: EmailAddressId,

    @Embedded
    private var emailAddress: EmailAddress
): AuditableAggregateRoot<EmailAddressId, EmailAddressEvent>(emailAddressId), Versioned {
    constructor(emailAddressId: EmailAddressId, emailAddress: String) : this(emailAddressId, EmailAddress(emailAddress, emailAddressId))

    constructor(emailAddressValueObject: EmailAddress) : this(emailAddressValueObject.emailId, emailAddressValueObject)

    @Version
    private var version: Long? = null

    override fun getVersion() = version

    fun getEmailAddress() = emailAddress

    fun updateAddress(newEmailAddress: String){
        emailAddress = EmailAddress(newEmailAddress, emailAddressId)
    }

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var emailGroupIds: MutableList<EmailGroupId> = mutableListOf()

    fun addTo(newEmailGroupId: EmailGroupId) {
        emailGroupIds.add(newEmailGroupId)
    }

    fun doesBelongsTo(emailGroupId: EmailGroupId): Boolean = emailGroupIds.contains(emailGroupId)

}