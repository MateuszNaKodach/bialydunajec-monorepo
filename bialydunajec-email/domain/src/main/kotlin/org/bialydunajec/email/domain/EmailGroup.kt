package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import javax.persistence.*

@Entity
@Table(schema = "email_group")
class EmailGroup(
        emailGroupId: EmailGroupId,
        private val name: String
) : AuditableAggregateRoot<EmailGroupId, EmailGroupEvent>(emailGroupId), Versioned {

    constructor(name: String) : this(EmailGroupId(), name)

    @Version
    private var version: Long? = null

    override fun getVersion() = version


}