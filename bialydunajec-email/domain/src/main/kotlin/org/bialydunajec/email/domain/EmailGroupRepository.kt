package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.email.domain.valueobject.EmailGroupName

interface EmailGroupRepository : DomainRepository<EmailGroup, EmailGroupId> {
    fun findByEmailAddressGroup(emailGroup: EmailGroupName): EmailGroup?
}
