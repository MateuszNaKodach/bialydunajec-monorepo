package org.bialydunajec.users.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(schema = "users")
class UserAccount internal constructor(
        userAccountId: UserAccountId,
        private var emailAddress: EmailAddress,
        private var firstName: FirstName,
        private var lastName: LastName
) : AuditableAggregateRoot<UserAccountId, UserAccountEvent>(userAccountId), Versioned {

    @Version
    private var version: Long? = null

    override fun getVersion() = version
}