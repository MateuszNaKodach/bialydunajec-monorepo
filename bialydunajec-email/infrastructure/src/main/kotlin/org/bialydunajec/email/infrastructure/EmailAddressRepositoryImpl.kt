package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.email.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
internal class EmailAddressRepositoryImpl(
        jpaRepository: EmailAddressJpaRepository
) : AbstractDomainRepositoryImpl<EmailAddress, EmailAddressId, EmailAddressJpaRepository>(jpaRepository),
        EmailAddressRepository {

    override fun findByEmailAddressValueEmail(address: String): EmailAddress? =
            jpaRepository.findByEmailAddressValueEmail(address)
}

internal interface EmailAddressJpaRepository : JpaRepository<EmailAddress, EmailAddressId> {
    fun findByEmailAddressValueEmail(address: String): EmailAddress?
}