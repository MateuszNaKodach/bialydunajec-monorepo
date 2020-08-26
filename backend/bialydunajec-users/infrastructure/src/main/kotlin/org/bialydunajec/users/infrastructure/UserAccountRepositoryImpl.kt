package org.bialydunajec.users.infrastructure

import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.users.domain.UserAccount
import org.bialydunajec.users.domain.UserAccountId
import org.bialydunajec.users.domain.UserAccountRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class UserAccountRepositoryImpl(
        jpaRepository: UserAccountJpaRepository,
        domainEventBus: DomainEventBus
) : AbstractDomainRepositoryImpl<UserAccount, UserAccountId, UserAccountJpaRepository>(jpaRepository, domainEventBus), UserAccountRepository {

}

internal interface UserAccountJpaRepository : JpaRepository<UserAccount, UserAccountId>
