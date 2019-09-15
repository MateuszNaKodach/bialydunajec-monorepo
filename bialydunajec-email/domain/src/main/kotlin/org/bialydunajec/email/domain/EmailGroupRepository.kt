package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup

interface EmailGroupRepository : DomainRepository<EmailGroup, EmailGroupId> {
    fun findByEmailGroup(emailGroup: EmailAddressGroup): EmailGroup?
}