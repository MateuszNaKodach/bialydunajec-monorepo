package org.bialydunajec.email.application.eventlistener

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.email.domain.EmailGroup
import org.bialydunajec.email.domain.EmailGroupEvent
import org.bialydunajec.email.domain.EmailGroupRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailGroupDomainEventListener(
        private val emailGroupRepository: EmailGroupRepository
) {
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleEmailGroupCreated(event: EmailGroupEvent.EmailGroupCreated) {
        val emailGroup = emailGroupRepository.findById(event.aggregateId)
                ?: event.emailGroup

        emailGroupRepository.save(emailGroup)
    }

}