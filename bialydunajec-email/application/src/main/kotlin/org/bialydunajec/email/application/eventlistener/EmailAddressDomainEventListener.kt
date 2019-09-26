package org.bialydunajec.email.application.eventlistener

import org.bialydunajec.email.domain.*
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailAddressDomainEventListener(
        private val emailAddressRepository: EmailAddressRepository
) {
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleEmailAddressCatalogizedToGroup(event: EmailAddressEvent.EmailAddressCatalogizedToEmailGroup) {
        val emailAddress = emailAddressRepository.findById(event.aggregateId)
                ?: EmailAddress(event.aggregateId, event.emailAddress)
        emailAddress.setPreviousEmailId(event.previousEmailAddressId)
        emailAddress.addTo(event.newEmailGroupId, event.emailAddressOwner)
        emailAddressRepository.save(emailAddress)
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleEmailAddressUpdated(event: EmailAddressEvent.EmailAddressUpdated) {
        val newAddressEmail = emailAddressRepository.findById(event.aggregateId)
                ?: EmailAddress(event.aggregateId, event.newEmailAddress)
        newAddressEmail.setPreviousEmailId(event.previousEmailAddressId)
        emailAddressRepository.save(newAddressEmail)
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleEmailAddressBelongingToGroupUpdated(event: EmailAddressEvent.EmailAddressBelongingToGroupUpdated) {
        val newEmailAddress = emailAddressRepository.findById(event.aggregateId)
                ?: EmailAddress(event.aggregateId, event.newEmailAddress)
        newEmailAddress.setPreviousEmailId(event.previousEmailAddressId)
        newEmailAddress.addTo(event.emailGroupId, event.emailOwner)
        emailAddressRepository.save(newEmailAddress)
    }

}