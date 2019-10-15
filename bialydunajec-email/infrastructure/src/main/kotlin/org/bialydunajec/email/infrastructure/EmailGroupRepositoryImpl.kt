package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.email.domain.EmailGroup
import org.bialydunajec.email.domain.EmailGroupId
import org.bialydunajec.email.domain.EmailGroupRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class EmailGroupRepositoryImpl(
        jpaRepository: EmailGroupJpaRepository
) : AbstractDomainRepositoryImpl<EmailGroup, EmailGroupId, EmailGroupJpaRepository>(jpaRepository), EmailGroupRepository

internal interface EmailGroupJpaRepository : JpaRepository<EmailGroup, EmailGroupId>
