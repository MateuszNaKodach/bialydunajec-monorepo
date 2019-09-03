package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.application.base.concurrency.ProcessingSerializedQueue
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantFactory
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
import java.time.LocalDate

@Transactional
@Service
internal class CampParticipantRegistrationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantPaymentFactory: CampParticipantCottageAccountFactory,
        private val campParticipantRepository: CampParticipantRepository,
        private val campEditionShirtRepository: CampEditionShirtRepository,
        private val shirtOrderRepository: ShirtOrderRepository,
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository,
        private val campParticipantCottageAccountRepository: CampParticipantCottageAccountRepository
) : ApplicationService<CampRegistrationsCommand.RegisterCampParticipantCommand> {

    private val processingQueue = ProcessingSerializedQueue<CampRegistrationsCommand.RegisterCampParticipantCommand> { processCommand(it) }

    override fun execute(command: CampRegistrationsCommand.RegisterCampParticipantCommand) =
            processingQueue.process(command)

    fun processCommand(command: CampRegistrationsCommand.RegisterCampParticipantCommand){
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

}

@Service
@Transactional
internal class UnregisterCampParticipantApplicationService(
        private val campParticipantRepository: CampParticipantRepository
) : ApplicationService<CampRegistrationsCommand.UnregisterCampParticipantByAuthorizedCommand> {

    override fun execute(command: CampRegistrationsCommand.UnregisterCampParticipantByAuthorizedCommand) {
        val campParticipant = campParticipantRepository.findById(command.campParticipantId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_TO_UNREGISTER__MUST_EXISTS)

        campParticipant.unregisterByAuthorized()

        campParticipantRepository.save(campParticipant)
        campParticipantRepository.delete(campParticipant)
    }

}

@Service
@Transactional
internal class CorrectCampParticipantRegistrationDataApplicationService(
        private val campParticipantRepository: CampParticipantRepository
) : ApplicationService<CampRegistrationsCommand.CorrectCampParticipantRegistrationDataCommand> {

    override fun execute(command: CampRegistrationsCommand.CorrectCampParticipantRegistrationDataCommand) {
        val campParticipant = campParticipantRepository.findById(command.campParticipantId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_MUST_EXISTS_TO_UPDATE_REGISTRATION_DATA)

        with(command.camperApplication) {
            campParticipant.correctCampParticipantData(
                    this.personalData.firstName.toString(),
                    this.personalData.lastName.toString(),
                    this.personalData.gender.toString(),
                    this.personalData.pesel.toString(),
                    this.personalData.birthDate?.toLocalDate(),
                    this.emailAddress.toString(),
                    this.phoneNumber.toString(),
                    this.homeAddress.postalCode.toString(),
                    this.homeAddress.city.toString(),
                    this.homeAddress.street.toString(),
                    this.homeAddress.homeNumber.toString(),
                    this.camperEducation.getHighSchool().toString(),
                    this.camperEducation.getIsHighSchoolRecentGraduate(),
                    this.camperEducation.getUniversity(),
                    this.camperEducation.getFieldOfStudy(),
                    this.camperEducation.getFaculty()
            )
            campParticipantRepository.save(campParticipant)
        }
    }
}