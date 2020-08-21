package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface EmailMessageLogRepository : DomainRepository<EmailMessageLog, EmailMessageLogId>, EmailMessageLogReadOnlyRepository