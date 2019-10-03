package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.email.domain.Email
import org.bialydunajec.email.domain.EmailId
import org.bialydunajec.email.domain.EmailRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
internal class EmailAddressRepositoryImpl(
    jpaRepository: EmailAddressJpaRepository
) : AbstractDomainRepositoryImpl<Email, EmailId, EmailAddressJpaRepository>(jpaRepository), EmailRepository

internal interface EmailAddressJpaRepository : JpaRepository<Email, EmailId>
