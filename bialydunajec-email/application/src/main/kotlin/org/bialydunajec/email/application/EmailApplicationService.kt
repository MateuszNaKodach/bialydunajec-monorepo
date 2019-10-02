package org.bialydunajec.email.application

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.email.application.api.EmailCommand
import org.bialydunajec.email.domain.*

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CatalogizeEmailAddressApplicationService(
    private val emailAddressRepository: EmailRepository
) : ApplicationService<EmailCommand.CatalogizeEmail> {

    override fun execute(command: EmailCommand.CatalogizeEmail) {
        val emailAddress = emailAddressRepository.findById(command.emailAddressId)
            ?: Email(
                command.emailAddressId,
                command.emailGroupId ?: EmailGroupId(),
                command.emailAddress,
                command.emailOwner
            )
        emailAddressRepository.save(emailAddress)
    }

}

@Service
@Transactional
internal class ChangeEmailAddressApplicationService(
    private val changeEmailValueDomainService: ChangeEmailAddressDomainService
) : ApplicationService<EmailCommand.ChangeEmailAddress> {

    override fun execute(command: EmailCommand.ChangeEmailAddress) {
        changeEmailValueDomainService.changeEmailValue(command.emailAddressId, command.newEmailAddress)
    }
}


@Service
@Transactional
internal class CorrectEmailOwnerApplicationService(
    private val emailAddressRepository: EmailRepository
) : ApplicationService<EmailCommand.CorrectEmailOwner> {

    override fun execute(command: EmailCommand.CorrectEmailOwner) {
        val emailAddress = emailAddressRepository.findById(command.emailAddressId)
            ?: throw DomainRuleViolationException.of(EmailAddressDomainRule.EMAIL_ADDRESS_TO_CORRECT_OWNER_MUST_EXISTS)
        emailAddress.correctOwner(command.emailOwner)
        emailAddressRepository.save(emailAddress)
    }
}


