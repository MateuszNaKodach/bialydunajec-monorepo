package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

/**
 * EmailRepository keeps rule that Email address should be unique in given Email group
 */
interface EmailRepository : DomainRepository<Email, EmailId>
