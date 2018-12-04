package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.persistence.ReadOnlyDomainRepository

interface EmailMessageLogReadOnlyRepository  : ReadOnlyDomainRepository<EmailMessageLog, EmailMessageLogId>