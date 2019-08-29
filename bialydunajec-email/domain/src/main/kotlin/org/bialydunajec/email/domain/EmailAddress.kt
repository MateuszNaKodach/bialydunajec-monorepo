package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress

import javax.persistence.*

@Entity
@Table(schema = "email_address")
class EmailAddress (
    val emailAddressId: EmailAddressId,

    @Embedded
    private var emailAddressValueObject: EmailAddress
): AuditableAggregateRoot<EmailAddressId, EmailAddressEvent>(emailAddressId), Versioned {

    constructor(emailAddressId: EmailAddressId, emailAddress: String) :
            this(emailAddressId,
                    EmailAddress(emailAddress, org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddressValueObjectId(emailAddressId.getIdentifierValue())))

    constructor(emailAddressValueObject: EmailAddress) :
            this(EmailAddressId(emailAddressValueObject.emailValueObjectId.getIdentifierValue()),
                    emailAddressValueObject)

    constructor(emailAddress: String) :this(EmailAddressId(), emailAddress)

    @Version
    private var version: Long? = null

    override fun getVersion() = version

    fun getEmailAddress() = emailAddressValueObject

    fun updateAddress(newEmailAddress: String){
        emailAddressValueObject = EmailAddress(newEmailAddress, emailAddressValueObject.emailValueObjectId)
    }

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var emailGroupIds: MutableList<EmailGroupId> = mutableListOf()

    fun addTo(newEmailGroupId: EmailGroupId) {
        emailGroupIds.add(newEmailGroupId)
    }

    fun doesBelongsTo(emailGroupId: EmailGroupId): Boolean = emailGroupIds.contains(emailGroupId)

}