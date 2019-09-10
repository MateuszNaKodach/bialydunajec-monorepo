package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import java.time.ZoneId

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

    fun addTo(newEmailGroupId: EmailGroupId) {
        emailGroupIds.add(newEmailGroupId)

        registerEvent(
                EmailAddressEvent.EmailAddressAddedToEmailGroup(
                        getAggregateId(),
                        newEmailGroupId
                )
        )
    }

    fun doesBelongsTo(emailGroupId: EmailGroupId): Boolean = emailGroupIds.contains(emailGroupId)

}