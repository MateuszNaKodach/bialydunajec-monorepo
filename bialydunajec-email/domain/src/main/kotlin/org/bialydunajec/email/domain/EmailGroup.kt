package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup
import javax.persistence.*

@Entity
@Table(schema = "email_group")
class EmailGroup(
        emailGroupId: EmailGroupId,
        val emailAddressGroup: EmailAddressGroup
) : AuditableAggregateRoot<EmailGroupId, EmailGroupEvent>(emailGroupId), Versioned {

    constructor(emailAddressGroup: EmailAddressGroup) : this(EmailGroupId(), emailAddressGroup)

    init {
        registerEvent(
                EmailGroupEvent.EmailGroupCreated(
                        getAggregateId(),
                        this
                )
        )
    }

    @Version
    private var version: Long? = null

    override fun getVersion() = version




}