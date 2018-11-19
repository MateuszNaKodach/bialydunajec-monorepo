package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountRepository
import org.bialydunajec.registrations.domain.payment.valueobject.PaymentCommitmentType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class PayCommitmentAndDepositMoneyApplicationService(
        private val campParticipantCottageAccountRepository: CampParticipantCottageAccountRepository
) : ApplicationService<CampRegistrationsCommand.PayCommitmentAndDepositMoney> {

    override fun execute(command: CampRegistrationsCommand.PayCommitmentAndDepositMoney) {
        campParticipantCottageAccountRepository.findById(command.campParticipantCottageAccountId)
                ?.apply {
                    when (command.type) {
                        PaymentCommitmentType.CAMP_DOWN_PAYMENT -> {
                            getCampDownPaymentCommitmentSnapshot()?.let {
                                depositMoney(it.amount, "Kwota zdeponowana przy zapłacie zadatku.")
                            }
                            payForCampDownPaymentWithAccountFunds()
                        }
                        PaymentCommitmentType.CAMP_PARTICIPATION -> {
                            getCampParticipationCommitmentSnapshot().let {
                                depositMoney(it.amount, "Kwota zdeponowana przy zapłacie reszty kwoty za udział w Obozie.")
                            }
                            payForCampParticipationWithAccountFunds()
                        }
                        PaymentCommitmentType.CAMP_BUS_SEAT -> {
                            getCampBusCommitmentSnapshot()?.let {
                                depositMoney(it.amount, "Kwota zdeponowana przy opłaceniu Autokaru Obozowego.")
                            }
                            payForCampBusWithAccountFunds()
                        }
                    }
                }?.also {
                    campParticipantCottageAccountRepository.save(it)
                }
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_COTTAGE_ACCOUNT_MUST_EXISTS_TO_MAKE_A_PAYMENT)
    }


}