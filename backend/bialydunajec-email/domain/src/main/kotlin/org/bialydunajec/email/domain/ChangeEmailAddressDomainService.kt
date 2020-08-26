package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.springframework.stereotype.Service


@Service
class ChangeEmailAddressDomainService(private val emailAddressRepository: EmailRepository) {

    fun changeEmailValue(emailAddressId: EmailId, newEmailAddress: EmailAddress) {
        val oldEmail = emailAddressRepository.findById(emailAddressId)
            ?: throw DomainRuleViolationException.of(EmailDomainRule.EMAIL_ADDRESS_TO_CORRECT_OWNER_MUST_EXISTS)
        val newEmail = oldEmail.newWithEmailAddress(newEmailAddress)
        emailAddressRepository.save(oldEmail)
        emailAddressRepository.save(newEmail)
    }

}
