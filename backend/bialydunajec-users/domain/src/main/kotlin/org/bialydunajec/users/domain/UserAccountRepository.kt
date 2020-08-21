package org.bialydunajec.users.domain

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface UserAccountRepository : DomainRepository<UserAccount, UserAccountId>