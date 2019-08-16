package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface EmailGroupRepository : DomainRepository<EmailGroup, EmailGroupId> {
}