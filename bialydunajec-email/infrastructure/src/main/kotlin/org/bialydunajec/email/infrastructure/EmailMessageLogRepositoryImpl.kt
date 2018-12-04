package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.email.domain.EmailMessageLog
import org.bialydunajec.email.domain.EmailMessageLogId
import org.bialydunajec.email.domain.EmailMessageLogRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
internal class EmailMessageLogRepositoryImpl(
        jpaRepository: EmailMessageLogJpaRepository
) : AbstractDomainRepositoryImpl<EmailMessageLog, EmailMessageLogId, EmailMessageLogJpaRepository>(jpaRepository),
        EmailMessageLogRepository {

}

internal interface EmailMessageLogJpaRepository : JpaRepository<EmailMessageLog, EmailMessageLogId> {
}