package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import javax.persistence.*
import javax.persistence.JoinColumn

@Entity
@Table(schema = "email_group")
class EmailGroup (
        emailGroupId: EmailGroupId,
        private val name: String
): AuditableAggregateRoot<EmailGroupId, EmailGroupEvent>(emailGroupId), Versioned {

    @Version
    private var version: Long? = null

    override fun getVersion() = version

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var emailAddresses: MutableList<EmailAddress> = mutableListOf()

    fun add(newEmailAddress: EmailAddress) {
        emailAddresses.add(newEmailAddress)
    }

    fun contains(emailAddress: EmailAddress): Boolean = emailAddresses.contains(emailAddress)

    fun getEmailAddressById(existingEmailAddressId: EmailAddressId): EmailAddress {
        return emailAddresses.find{it.getAggregateId() == existingEmailAddressId}!!
    }

}