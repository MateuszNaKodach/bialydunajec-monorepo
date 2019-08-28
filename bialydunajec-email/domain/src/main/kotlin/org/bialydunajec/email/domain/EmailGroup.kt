package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddressId
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



}