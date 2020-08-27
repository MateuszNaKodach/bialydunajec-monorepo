package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.validation.DomainRuleChecker
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import javax.persistence.*

@Entity
@Table(schema = "email")
class Email(
    @Embedded
    val groupId: EmailGroupId,
    @Embedded
    val address: EmailAddress,
    owner: EmailAddressOwner,
    emailId: EmailId =  EmailId.from(address, groupId)
) : AuditableAggregateRoot<EmailId, EmailEvent>(emailId), Versioned {

    @Embedded
    var owner: EmailAddressOwner = owner
        private set

    @AttributeOverrides(
        AttributeOverride(name = "aggregateId", column = Column(name = "newEmailId"))
    )
    var newEmailId: EmailId? = null
        private set

    private val isActive: Boolean
        get() = newEmailId == null

    init {
        registerEvent(
            EmailEvent.EmailCatalogized(
                getAggregateId(),
                groupId,
                address,
                owner
            )
        )
    }

    fun correctOwner(emailOwner: EmailAddressOwner) {
        if (this.owner !== emailOwner) {
            this.owner = emailOwner
            registerEvent(
                EmailEvent.EmailOwnerCorrected(
                    getAggregateId(),
                    groupId,
                    address,
                    owner
                )
            )
        }
    }

    fun newWithEmailAddress(newEmailAddress: EmailAddress): Email {
        DomainRuleChecker.check(EmailDomainRule.EMAIL_ADDRESS_TO_DEACTIVATE_MUST_BE_ACTIVE) { isActive }
        if (newEmailAddress == this.address) {
            return this
        }
        return Email(
            this.groupId,
            newEmailAddress,
            this.owner
        ).also {
            newEmailId = it.getAggregateId()
            registerEvent(
                EmailEvent.EmailAddressChanged(
                    getAggregateId(),
                    groupId,
                    address,
                    newEmailAddress,
                    it.getAggregateId(),
                    owner
                )
            )
        }
    }

    @Version
    private var version: Long? = null

    override fun getVersion() = version
}
