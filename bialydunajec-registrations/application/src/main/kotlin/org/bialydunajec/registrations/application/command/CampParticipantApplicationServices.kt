package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantFactory
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistration
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountFactory
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountRepository
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.bialydunajec.registrations.domain.shirt.ShirtOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CampParticipantRegistrationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantPaymentFactory: CampParticipantCottageAccountFactory,
        private val campParticipantRepository: CampParticipantRepository,
        private val campEditionShirtRepository: CampEditionShirtRepository,
        private val shirtOrderRepository: ShirtOrderRepository,
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository,
        private val campParticipantCottageAccountRepository: CampParticipantCottageAccountRepository
) : ApplicationService<CampRegistrationsCommand.RegisterCampParticipantCommand> {

    override fun execute(command: CampRegistrationsCommand.RegisterCampParticipantCommand): CampParticipantId =
            campParticipantFactory.createCampParticipant(command.campRegistrationsEditionId, command.camperApplication)
                    .let { campParticipantRepository.save(it) }
                    .also { campParticipant ->
                        val campEditionShirt = campEditionShirtRepository.findByCampRegistrationsEditionId(command.campRegistrationsEditionId)
                                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_ORDER_MUST_EXISTS)
                        val shirtOrder = campEditionShirt.placeOrder(campParticipant, command.shirtOrder.shirtColorOptionId, command.shirtOrder.shirtSizeOptionId)
                        shirtOrderRepository.save(shirtOrder)
                        campParticipantRegistrationRepository.save(CampParticipantRegistration.createFrom(campParticipant.getSnapshot(), shirtOrder.getSnapshot()))
                    }
                    .also {
                        val campParticipantCottageAccount = campParticipantPaymentFactory.createFor(it)
                        campParticipantCottageAccountRepository.save(campParticipantCottageAccount)
                    }
                    .getAggregateId()


}