package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

import javax.persistence.*

@Entity
@Table(schema = "email_address")
class EmailAddress(
        emailAddressId: EmailAddressId,

        @Embedded
        private var emailAddressValue: EmailAddress,
        private var isActive: Boolean = true,
        private var previousEmailAddresId: EmailAddressId? = null
) : AuditableAggregateRoot<EmailAddressId, EmailAddressEvent>(emailAddressId), Versioned {

    constructor(emailAddressId: EmailAddressId, emailAddress: String) :
            this(emailAddressId,
                    EmailAddress(emailAddress))

    constructor(emailAddress: EmailAddress) :
            this(EmailAddressId.from(emailAddress), emailAddress)

    constructor(emailAddress: EmailAddress, emailAddressGroupToBeCatalogizedIn: EmailAddressGroup, previousEmailAddressId: EmailAddressId?) :
            this(
                    EmailAddressId.from(emailAddress, emailAddressGroupToBeCatalogizedIn),
                    emailAddress,
                    true,
                    previousEmailAddressId
            )

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

    fun deactivateEmailAddress(){
        isActive = false

        registerEvent(
                EmailAddressEvent.EmailAddressDeactivated(
                        getAggregateId(),
                        emailAddressValue
                )
        )

    }


    @ElementCollection
    var emailGroupIds: MutableSet<EmailGroupId> = mutableSetOf()

    var emailOwner: EmailAddressOwner? = null

    fun addTo(newEmailGroup: EmailGroup, emailAddressOwner: EmailAddressOwner) {

        val isAdded = emailGroupIds.add(newEmailGroup.getAggregateId())

        if (isAdded) {
            emailOwner = emailAddressOwner
            registerEvent(
                    EmailAddressEvent.EmailAddressCatalogizedToEmailGroup(
                            getAggregateId(),
                            emailAddressValue,
                            newEmailGroup.getAggregateId(),
                            newEmailGroup.emailAddressGroup,
                            emailAddressOwner
                    )
            )
        }
    }

    fun belongsTo(emailGroupId: EmailGroupId): Boolean = emailGroupIds.contains(emailGroupId)

}