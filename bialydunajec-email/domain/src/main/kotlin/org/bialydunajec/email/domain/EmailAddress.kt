package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

import javax.persistence.*

@Entity
@Table(schema = "email_address")
class EmailAddress(
        emailAddressId: EmailAddressId,

        @Embedded
        private var emailAddressValue: EmailAddress
) : AuditableAggregateRoot<EmailAddressId, EmailAddressEvent>(emailAddressId), Versioned {

    constructor(emailAddressId: EmailAddressId, emailAddress: String) :
            this(emailAddressId,
                    EmailAddress(emailAddress, org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddressId(emailAddressId.getIdentifierValue())))

    constructor(emailAddressValueObject: EmailAddress) :
            this(EmailAddressId(emailAddressValueObject.emailAddressId.getIdentifierValue()),
                    emailAddressValueObject)

    constructor(emailAddress: String) : this(EmailAddressId(), emailAddress)

    init {
        registerEvent(
                EmailAddressEvent.EmailAddressCreated(
                        getAggregateId(),
                        emailAddressValue
                )
        )
    }

    @Version
    private var version: Long? = null

    override fun getVersion() = version

    fun getEmailAddress() = emailAddressValue

    fun updateAddress(newEmailAddress: String) {
        emailAddressValue = EmailAddress(newEmailAddress, emailAddressValue.emailAddressId)

        registerEvent(
                EmailAddressEvent.EmailAddressUpdated(
                        getAggregateId(),
                        emailAddressValue
                )
        )
    }

    @ElementCollection
    var emailGroupIds: MutableSet<EmailGroupId> = mutableSetOf()

    fun addTo(newEmailGroupId: EmailGroupId, emailAddressOwner: EmailAddressOwner) {
        val isAdded = emailGroupIds.add(newEmailGroupId)

        if (isAdded) {
            registerEvent(
                    EmailAddressEvent.EmailAddressCatalogizedToEmailGroup(
                            getAggregateId(),
                            newEmailGroupId,
                            emailAddressOwner
                    )
            )
        }
    }

    fun doesBelongsTo(emailGroupId: EmailGroupId): Boolean = emailGroupIds.contains(emailGroupId)

}