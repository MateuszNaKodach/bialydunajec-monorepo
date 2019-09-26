package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

import javax.persistence.*

@Entity
@Table(schema = "email_address")
class EmailAddress(
        emailAddressId: EmailAddressId,

        @Embedded
        private var emailAddressValue: EmailAddress,
        private var isActive: Boolean = true
) : AuditableAggregateRoot<EmailAddressId, EmailAddressEvent>(emailAddressId), Versioned {

    constructor(emailAddressId: EmailAddressId, emailAddress: String) :
            this(emailAddressId,
                    EmailAddress(emailAddress))

    constructor(emailAddress: EmailAddress) :
            this(EmailAddressId.from(emailAddress), emailAddress)

    constructor(emailAddress: String) : this(EmailAddress(emailAddress))

    init {
        registerEvent(
                EmailAddressEvent.EmailAddressCreated(
                        getAggregateId(),
                        emailAddressValue,
                        isActive
                )
        )
    }

    @Version
    private var version: Long? = null

    override fun getVersion() = version

    fun getEmailAddress() = emailAddressValue

    var emailGroupId: EmailGroupId? = null

    var emailOwner: EmailAddressOwner? = null

    var previousEmailAddressId: EmailAddressId? = null

    fun deactivateEmailAddress() {
        if(isActive) {
            isActive = false
            registerEvent(
                    EmailAddressEvent.EmailAddressDeactivated(
                            getAggregateId(),
                            emailAddressValue,
                            emailGroupId
                    )
            )
        }
    }

    fun catalogizeTo(newEmailGroup: EmailGroup, emailAddressOwner: EmailAddressOwner) {

        deactivateEmailAddress()

        val newAddressEmailId: EmailAddressId = EmailAddressId.from(emailAddressValue, newEmailGroup.emailAddressGroup)

        registerEvent(
                EmailAddressEvent.EmailAddressCatalogizedToEmailGroup(
                        newAddressEmailId,
                        emailAddressValue,
                        getAggregateId(),
                        newEmailGroup.getAggregateId(),
                        newEmailGroup.emailAddressGroup,
                        emailAddressOwner
                )
        )
    }

    fun addTo(newEmailGroupId: EmailGroupId, emailAddressOwner: EmailAddressOwner?){
        if (emailGroupId == null || emailGroupId != newEmailGroupId) {
            emailGroupId = newEmailGroupId
            emailOwner = emailAddressOwner
        }
    }

    fun setPreviousEmailId(previousEmailAddressId: EmailAddressId){
        this.previousEmailAddressId = previousEmailAddressId
    }

    fun belongsTo(emailGroupId: EmailGroupId): Boolean = emailGroupId == emailGroupId

    fun updateEmailAddress(newEmailAddress: EmailAddress, emailGroupToWhichOldEmailBelongs: EmailGroup?) {

        deactivateEmailAddress()

        if (emailGroupToWhichOldEmailBelongs != null) {
            val newEmailAddressId = EmailAddressId.from(newEmailAddress, emailGroupToWhichOldEmailBelongs.emailAddressGroup)

            EmailAddressEvent.EmailAddressBelongingToGroupUpdated(
                    newEmailAddressId,
                    newEmailAddress,
                    getAggregateId(),
                    emailGroupToWhichOldEmailBelongs.getAggregateId(),
                    emailGroupToWhichOldEmailBelongs.emailAddressGroup,
                    emailOwner
            )

        } else {
            val newEmailAddressId = EmailAddressId.from(newEmailAddress)
            EmailAddressEvent.EmailAddressUpdated(
                    newEmailAddressId,
                    newEmailAddress,
                    getAggregateId()
            )
        }

    }

}