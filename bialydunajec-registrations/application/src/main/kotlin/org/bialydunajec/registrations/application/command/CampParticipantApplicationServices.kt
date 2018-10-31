package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantFactory
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistration
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentFactory
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CampParticipantRegistrationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantPaymentFactory: CampParticipationPaymentFactory,
        private val campParticipantRepository: CampParticipantRepository,
        private val campEditionShirtRepository: CampEditionShirtRepository,
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository,
        private val campParticipationPaymentRepository: CampParticipationPaymentRepository
) : ApplicationService<CampRegistrationsCommand.RegisterCampParticipantCommand> {

    override fun process(command: CampRegistrationsCommand.RegisterCampParticipantCommand): CampParticipantId =
            campParticipantFactory.createCampParticipant(command.campRegistrationsEditionId, command.camperApplication)
                    .let { campParticipantRepository.save(it) }
                    .also { campParticipant ->
                        /* FIXME: Coś jest nie tak, bo tworzę i modyfikuję agregat, przez zmianę czegoś, np. dodanie nowej koszulki bedzie blad transakcji
                            zrobić tak, że ShirtOrder jest innym agregatem.
                        */
                        val campEditionShirt = campEditionShirtRepository.findByCampRegistrationsEditionId(command.campRegistrationsEditionId)
                                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_ORDER_MUST_EXISTS)
                        val placedOrderSnapshot = campEditionShirt.placeOrder(campParticipant, command.shirtOrder.shirtColorOptionId, command.shirtOrder.shirtSizeOptionId)
                        campEditionShirtRepository.save(campEditionShirt)
                        campParticipantRegistrationRepository.save(CampParticipantRegistration.createFrom(campParticipant.getSnapshot(), placedOrderSnapshot))
                    }
                    .also {
                        val payment = campParticipantPaymentFactory.createFor(it)
                        campParticipationPaymentRepository.save(payment)
                    }
                    .getAggregateId()


}