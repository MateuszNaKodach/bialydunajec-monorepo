package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.base.validation.DomainRuleChecker
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(schema = "email")
class Email(
    emailAddressId: EmailId,
    @Embedded
    val groupId: EmailGroupId,
    @Embedded
    val address: EmailAddress,
    owner: EmailAddressOwner
) : AuditableAggregateRoot<EmailId, EmailEvent>(emailAddressId), Versioned {

    @Embedded
    var owner: EmailAddressOwner = owner
        private set

    @Embedded
    var newEmailId: EmailId? = null
        private set

    private val isActive: Boolean
        get() = newEmailId == null

    init {
        registerEvent(
            EmailEvent.EmailCatalogized(
                getAggregateId(),
                address,
                groupId,
                owner
            )
        )
    }

    fun correctOwner(emailOwner: EmailAddressOwner) {
        this.owner = emailOwner
        registerEvent(
            EmailEvent.EmailOwnerCorrected(
                getAggregateId(),
                address,
                owner
            )
        )
    }

    fun newWithEmailAddress(newEmailAddress: EmailAddress): Email {
        DomainRuleChecker.check(EmailAddressDomainRule.EMAIL_ADDRESS_TO_DEACTIVATE_MUST_BE_ACTIVE) { isActive }
        return Email(
            EmailId.from(newEmailAddress, this.groupId),
            this.groupId,
            newEmailAddress,
            this.owner
        ).also {
            newEmailId = it.getAggregateId()
            registerEvent(
                EmailEvent.EmailAddressChanged(
                    getAggregateId(),
                    newEmailAddress,
                    it.getAggregateId()
                )
            )
        }
    }

    fun belongsTo(emailGroupId: EmailGroupId): Boolean = groupId == emailGroupId

    @Version
    private var version: Long? = null

    override fun getVersion() = version
}
