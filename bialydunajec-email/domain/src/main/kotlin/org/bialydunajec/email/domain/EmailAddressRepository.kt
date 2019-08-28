package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddressId

interface EmailAddressRepository: DomainRepository<EmailAddress, EmailAddressId> {

    fun findByAddressEmailString(address: String): EmailAddress?
}