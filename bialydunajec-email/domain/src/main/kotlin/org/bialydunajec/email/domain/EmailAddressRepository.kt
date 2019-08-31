package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface EmailAddressRepository : DomainRepository<EmailAddress, EmailAddressId> {

    fun findByAddressEmailByAddress(address: String): EmailAddress?
}