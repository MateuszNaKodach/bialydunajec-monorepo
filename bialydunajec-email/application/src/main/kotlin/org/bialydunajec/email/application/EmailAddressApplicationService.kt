package org.bialydunajec.email.application

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.email.application.api.EmailAddressCommand
import org.bialydunajec.email.application.api.EmailAddressCommandGateway
import org.bialydunajec.email.domain.*

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CatalogizeEmailAddressApplicationService(
        private val emailGroupRepository: EmailGroupRepository,
        private val emailAddressRepository: EmailAddressRepository,
        private val emailAddressCommandGateway: EmailAddressCommandGateway
) : ApplicationService<EmailAddressCommand.CatalogizeEmailAddress> {

    override fun execute(command: EmailAddressCommand.CatalogizeEmailAddress) {

        emailAddressCommandGateway.process(EmailAddressCommand.DeactivateEmailAddress(
                EmailAddressId.from(command.emailAddress),
                command.emailAddress
        )
        )

        val emailGroup = emailGroupRepository.findByEmailGroup(command.emailGroup)
                ?: EmailGroup(command.emailGroup)

        val emailAddressToBeCatalogized = emailAddressRepository.findById(
                EmailAddressId.from(command.emailAddress, command.emailGroup))
                ?: EmailAddress(
                        command.emailAddress,
                        command.emailGroup,
                        EmailAddressId.from(command.emailAddress)
                )


        emailAddressToBeCatalogized.addTo(
                emailGroup,
                command.emailAddressOwner)

        emailAddressRepository.save(emailAddressToBeCatalogized)

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

@Service
@Transactional
internal class DeactivateEmailAddressApplicationService(
        private val emailAddressRepository: EmailAddressRepository
) : ApplicationService<EmailAddressCommand.DeactivateEmailAddress> {

    override fun execute(command: EmailAddressCommand.DeactivateEmailAddress) {

        val emailAddress = emailAddressRepository.findById(command.emailAddressId)
                ?: throw DomainRuleViolationException.of(EmailAddressDomainRule.EMAIL_ADDRESS_TO_DEACTIVATE_MUST_EXISTS)

        emailAddress.deactivateEmailAddress()

        emailAddressRepository.save(emailAddress)
    }
}

