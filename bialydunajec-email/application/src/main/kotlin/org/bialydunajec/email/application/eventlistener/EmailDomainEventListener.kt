package org.bialydunajec.email.application.eventlistener

import org.bialydunajec.email.domain.EmailEvent
import org.bialydunajec.email.domain.EmailGroup
import org.bialydunajec.email.domain.EmailGroupId
import org.bialydunajec.email.domain.EmailGroupRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailDomainEventListener(
    private val emailGroupRepository: EmailGroupRepository
) {

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handle(event: EmailEvent.EmailCatalogized) {
        createEmailGroupIfNotExists(event.emailGroupId)
    }

    private fun createEmailGroupIfNotExists(emailGroupId: EmailGroupId) {
        if (!emailGroupRepository.existsById(emailGroupId)) {
            emailGroupRepository.save(EmailGroup(emailGroupId))
        }
    }

}
