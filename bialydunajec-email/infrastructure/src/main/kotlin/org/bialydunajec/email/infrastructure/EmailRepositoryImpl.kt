package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.email.domain.Email
import org.bialydunajec.email.domain.EmailId
import org.bialydunajec.email.domain.EmailRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
internal class EmailRepositoryImpl(
    jpaRepository: EmailJpaRepository
) : AbstractDomainRepositoryImpl<Email, EmailId, EmailJpaRepository>(jpaRepository), EmailRepository

internal interface EmailJpaRepository : JpaRepository<Email, EmailId>
