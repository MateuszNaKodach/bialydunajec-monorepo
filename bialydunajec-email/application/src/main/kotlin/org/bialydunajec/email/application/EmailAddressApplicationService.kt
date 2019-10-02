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
    private val emailAddressRepository: EmailRepository
) : ApplicationService<EmailAddressCommand.CatalogizeEmailAddress> {

    override fun execute(command: EmailAddressCommand.CatalogizeEmailAddress) {
        val emailAddress = emailAddressRepository.findById(command.emailAddressId)
            ?: Email(
                command.emailAddressId,
                command.emailGroupId ?: EmailGroupId(),
                command.email,
                command.emailOwner
            )
        emailAddressRepository.save(emailAddress)
    }

}

@Service
@Transactional
internal class ChangeEmailAddressValueApplicationService(
    private val changeEmailValueDomainService: ChangeEmailAddressDomainService
) : ApplicationService<EmailAddressCommand.ChangeEmailAddressValue> {

    override fun execute(command: EmailAddressCommand.ChangeEmailAddressValue) {
        changeEmailValueDomainService.changeEmailValue(command.emailAddressId, command.newEmail)
    }
}


@Service
@Transactional
internal class CorrectEmailAddressOwnerApplicationService(
    private val emailAddressRepository: EmailRepository
) : ApplicationService<EmailAddressCommand.UpdateEmailAddressOwner> {

    override fun execute(command: EmailAddressCommand.UpdateEmailAddressOwner) {
        val emailAddress = emailAddressRepository.findById(command.emailAddressId)
            ?: throw DomainRuleViolationException.of(EmailAddressDomainRule.EMAIL_ADDRESS_TO_CORRECT_OWNER_MUST_EXISTS)
        emailAddress.correctOwner(command.emailOwner)
        emailAddressRepository.save(emailAddress)
    }
}


