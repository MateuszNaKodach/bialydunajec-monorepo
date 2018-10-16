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
internal class CampParticipantRegistrationConfirmApplicationService(
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository
) : ApplicationService<CampRegistrationsCommand.CampParticipantRegistrationConfirmCommand> {

    override fun process(command: CampRegistrationsCommand.CampParticipantRegistrationConfirmCommand) {
        val campParticipantRegistration = campParticipantRegistrationRepository.findById(command.campParticipantRegistrationId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_REGISTRATIONS_TO_CONFIRM_MUST_EXISTS)

        campParticipantRegistration.confirmByCamperWithVerificationCode(command.verificationCode)

        campParticipantRegistrationRepository.save(campParticipantRegistration)
    }


}