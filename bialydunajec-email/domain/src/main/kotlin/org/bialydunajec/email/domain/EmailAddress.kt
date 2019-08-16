package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(schema = "email_address")
class EmailAddress (
    emailAddressId: EmailAddressId,
    private var emailAddress: String
): AuditableAggregateRoot<EmailAddressId, EmailAddressEvent>(emailAddressId), Versioned {

    @Version
    private var version: Long? = null

    override fun getVersion() = version
}