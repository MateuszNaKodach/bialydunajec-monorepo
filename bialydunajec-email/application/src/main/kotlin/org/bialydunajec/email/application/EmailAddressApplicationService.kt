package org.bialydunajec.email.application

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.email.application.api.EmailAddressCommand
import org.bialydunajec.email.domain.*

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CatalogizeEmailAddressApplicationService(
        private val emailGroupRepository: EmailGroupRepository,
        private val emailAddressRepository: EmailAddressRepository
) : ApplicationService<EmailAddressCommand.CatalogizeEmailAddress> {

    override fun execute(command: EmailAddressCommand.CatalogizeEmailAddress) {
        val emailAddress = emailAddressRepository.findByEmailAddressValueEmail(command.emailAddress.toString())
                ?: EmailAddress(command.emailAddress)

        val emailGroup = emailGroupRepository.findByGroupName(command.emailGroupName)
                ?: EmailGroup(command.emailGroupName)
        
        emailAddress.addTo(
                emailGroup.getAggregateId(),
                command.emailAddressOwner)

        emailAddressRepository.save(emailAddress)
        emailGroupRepository.save(emailGroup)
    }
}

@Service
@Transactional
internal class UpdateEmailAddressApplicationService(
        private val emailAddressRepository: EmailAddressRepository
) : ApplicationService<EmailAddressCommand.UpdateEmailAddress> {

    override fun execute(command: EmailAddressCommand.UpdateEmailAddress) {

        val emailAddress = emailAddressRepository.findById(command.emailAddressId)
                ?: throw DomainRuleViolationException.of(EmailAddressDomainRule.EMAIL_ADDRESS_TO_UPDATE_MUST_EXISTS)

        emailAddress.updateAddress(command.newEmailAddress)

        emailAddressRepository.save(emailAddress)
    }
}

