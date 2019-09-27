package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.email.domain.*
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class EmailGroupRepositoryImpl(
        jpaRepository: EmailGroupJpaRepository
) : AbstractDomainRepositoryImpl<EmailGroup, EmailGroupId, EmailGroupJpaRepository>(jpaRepository),
        EmailGroupRepository {
    override fun findByEmailAddressGroup(emailGroup: EmailAddressGroup): EmailGroup?  = jpaRepository.findByEmailAddressGroup(emailGroup)
}

internal interface EmailGroupJpaRepository : JpaRepository<EmailGroup, EmailGroupId> {
    fun findByEmailAddressGroup(emailGroup: EmailAddressGroup): EmailGroup?
}