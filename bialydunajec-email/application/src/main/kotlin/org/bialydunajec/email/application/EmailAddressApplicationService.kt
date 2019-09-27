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

        val emailGroup = emailGroupRepository.findByEmailAddressGroup(command.emailGroup)
                ?: EmailGroup(command.emailGroup)

        val emailAddressToBeCatalogized = emailAddressRepository.findById(
                EmailAddressId.from(command.emailAddress))
                ?: EmailAddress(command.emailAddress)

        emailAddressToBeCatalogized.catalogizeTo(
                emailGroup,
                command.emailAddressOwner)

        emailAddressRepository.save(emailAddressToBeCatalogized)
    }
}

@Service
@Transactional
internal class UpdateEmailAddressApplicationService(
        private val emailAddressRepository: EmailAddressRepository,
        private val emailGroupRepository: EmailGroupRepository
) : ApplicationService<EmailAddressCommand.UpdateEmailAddress> {

    override fun execute(command: EmailAddressCommand.UpdateEmailAddress) {
        val oldEmailAddress = emailAddressRepository.findById(command.emailAddressId)
                ?: throw DomainRuleViolationException.of(EmailAddressDomainRule.EMAIL_ADDRESS_TO_UPDATE_MUST_EXISTS)
        val emailGroup = emailGroupRepository.findById(oldEmailAddress.emailGroupId!!)
        oldEmailAddress.updateEmailAddress(command.newEmailAddress, emailGroup)

        emailAddressRepository.save(oldEmailAddress)
    }
}


