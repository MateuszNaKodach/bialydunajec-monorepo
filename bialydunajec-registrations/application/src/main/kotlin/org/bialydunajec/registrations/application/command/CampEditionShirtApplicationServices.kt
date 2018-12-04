package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.bialydunajec.registrations.domain.shirt.ShirtOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class UpdateCampEditionShirtApplicationService(
        private val campEditionShirtRepository: CampEditionShirtRepository
) : ApplicationService<CampRegistrationsCommand.UpdateCampEditionShirt> {

    override fun execute(command: CampRegistrationsCommand.UpdateCampEditionShirt) {
        val campEditionShirt = campEditionShirtRepository.findById(command.campEditionShirtId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_UPDATE_MUST_EXISTS)
        campEditionShirt.update(command.shirtSizesFileUrl)
        campEditionShirtRepository.save(campEditionShirt)
    }

}

@Service
@Transactional
internal class AddCampEditionShirtColorOptionApplicationService(
        private val campEditionShirtRepository: CampEditionShirtRepository
) : ApplicationService<CampRegistrationsCommand.AddCampEditionShirtColorOption> {

    override fun execute(command: CampRegistrationsCommand.AddCampEditionShirtColorOption) {
        val campEditionShirt = campEditionShirtRepository.findById(command.campEditionShirtId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_UPDATE_MUST_EXISTS)
        campEditionShirt.addColorOption(command.color, command.available)
        campEditionShirtRepository.save(campEditionShirt)
    }

}

@Service
@Transactional
internal class UpdateCampEditionShirtColorOptionApplicationService(
        private val campEditionShirtRepository: CampEditionShirtRepository
) : ApplicationService<CampRegistrationsCommand.UpdateCampEditionShirtColorOption> {

    override fun execute(command: CampRegistrationsCommand.UpdateCampEditionShirtColorOption) {
        val campEditionShirt = campEditionShirtRepository.findById(command.campEditionShirtId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_UPDATE_MUST_EXISTS)
        campEditionShirt.updateColorOption(command.shirtColorOptionId, command.color, command.available)
        campEditionShirtRepository.save(campEditionShirt)
    }

}

@Service
@Transactional
internal class AddCampEditionShirtSizeOptionApplicationService(
        private val campEditionShirtRepository: CampEditionShirtRepository
) : ApplicationService<CampRegistrationsCommand.AddCampEditionShirtSizeOption> {

    override fun execute(command: CampRegistrationsCommand.AddCampEditionShirtSizeOption) {
        val campEditionShirt = campEditionShirtRepository.findById(command.campEditionShirtId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_UPDATE_MUST_EXISTS)
        campEditionShirt.addSizeOption(command.size, command.available)
        campEditionShirtRepository.save(campEditionShirt)
    }

}


@Service
@Transactional
internal class UpdateCampEditionShirtSizeOptionApplicationService(
        private val campEditionShirtRepository: CampEditionShirtRepository
) : ApplicationService<CampRegistrationsCommand.UpdateCampEditionShirtSizeOption> {

    override fun execute(command: CampRegistrationsCommand.UpdateCampEditionShirtSizeOption) {
        val campEditionShirt = campEditionShirtRepository.findById(command.campEditionShirtId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_UPDATE_MUST_EXISTS)
        campEditionShirt.updateSizeOption(command.shirtSizeOptionId, command.size, command.available)
        campEditionShirtRepository.save(campEditionShirt)
    }

}

@Service
@Transactional
internal class PlaceCampEditionShirtOrderApplicationService(
        private val campParticipantRepository: CampParticipantRepository,
        private val campEditionShirtRepository: CampEditionShirtRepository,
        private val shirtOrderRepository: ShirtOrderRepository
) : ApplicationService<CampRegistrationsCommand.PlaceCampEditionShirtOrder> {

    override fun execute(command: CampRegistrationsCommand.PlaceCampEditionShirtOrder) {
        val campEditionShirt = campEditionShirtRepository.findById(command.campEditionShirtId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_ORDER_MUST_EXISTS)
        val campParticipant = campParticipantRepository.findById(command.campParticipantId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_CAN_BE_ORDERED_ONLY_FOR_EXISTING_CAMP_PARTICIPANT)

        campEditionShirt.placeOrder(campParticipant, command.color, command.size)
                .let { shirtOrderRepository.save(it) }
    }

}