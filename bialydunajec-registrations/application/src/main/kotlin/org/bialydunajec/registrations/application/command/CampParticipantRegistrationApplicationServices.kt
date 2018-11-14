package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class VerifyCampParticipantRegistrationApplicationService(
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository
) : ApplicationService<CampRegistrationsCommand.VerifyCampParticipantRegistrationCommand> {

    override fun execute(command: CampRegistrationsCommand.VerifyCampParticipantRegistrationCommand) {
        val campParticipantRegistration = campParticipantRegistrationRepository.findById(command.campParticipantRegistrationId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_REGISTRATIONS_TO_CONFIRM_MUST_EXISTS)

        campParticipantRegistration.verifyByCamperWithCode(command.verificationCode)

        campParticipantRegistrationRepository.save(campParticipantRegistration)
    }

}

@Service
@Transactional
internal class VerifyCampParticipantRegistrationByAuthorizedApplicationService(
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository
) : ApplicationService<CampRegistrationsCommand.VerifyCampParticipantRegistrationCommandByAuthorized> {

    override fun execute(command: CampRegistrationsCommand.VerifyCampParticipantRegistrationCommandByAuthorized) {
        val campParticipantRegistration = campParticipantRegistrationRepository.findById(command.campParticipantRegistrationId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_REGISTRATIONS_TO_CONFIRM_MUST_EXISTS)

        campParticipantRegistration.verifyByAuthorized()

        campParticipantRegistrationRepository.save(campParticipantRegistration)
    }

}