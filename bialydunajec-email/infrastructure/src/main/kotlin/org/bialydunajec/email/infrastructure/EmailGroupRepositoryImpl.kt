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

    override fun findByEmailGroup(emailGroup: EmailAddressGroup): EmailGroup? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

internal interface EmailGroupJpaRepository : JpaRepository<EmailGroup, EmailGroupId> {
}