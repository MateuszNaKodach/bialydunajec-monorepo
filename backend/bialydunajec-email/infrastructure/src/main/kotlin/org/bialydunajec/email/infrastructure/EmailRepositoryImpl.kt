package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.email.domain.Email
import org.bialydunajec.email.domain.EmailDomainRule
import org.bialydunajec.email.domain.EmailId
import org.bialydunajec.email.domain.EmailRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
internal class EmailRepositoryImpl(
    jpaRepository: EmailJpaRepository,
    domainEventBus: DomainEventBus
) : AbstractDomainRepositoryImpl<Email, EmailId, EmailJpaRepository>(jpaRepository, domainEventBus), EmailRepository {

    override fun save(aggregateRoot: Email): Email =
        try {
            super.save(aggregateRoot)
        } catch (e: DataIntegrityViolationException) {
            throw DomainRuleViolationException.of(EmailDomainRule.EMAIL_ADDRESS_CAN_BE_CATAGOLIZED_ONLY_ONCE_AT_THE_SAME_GROUP)
        }

}

internal interface EmailJpaRepository : JpaRepository<Email, EmailId>
