package org.bialydunajec.registrations.domain.camper.campparticipant

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.campedition.specification.InProgressCampRegistrationsSpecification
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistration
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationEvent
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.cottage.specification.ActivatedCottageSpecification
import org.bialydunajec.registrations.domain.cottage.specification.CottageFreeSpaceSpecificationFactory
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule.*
import org.springframework.stereotype.Component

//TODO: Id Campera jest zwiazane z CampRegistrations
//TODO: Dodać metode sprawdzajaca czy pesel sie nie powtarza, tworzenie id z hashowaniem peselu BCrypt - dla trzymania danych archiwalnych, ew. random!
//TODO: Remove duplicated code from CampParticipantRegistrationFactory!!!
@Component
class CampParticipantFactory constructor(
        private val cottageRepository: CottageRepository,
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val cottageFreeSpaceSpecificationFactory: CottageFreeSpaceSpecificationFactory,
        private val campParticipantRepository: CampParticipantRepository,
        private val campParticipantIdGenerator: CampParticipantIdGenerator
) {


    fun createCampParticipant(
            campRegistrationsEditionId: CampRegistrationsEditionId,
            camperApplication: CamperApplication,
            stayDuration: StayDuration? = null
    ): CampParticipant {
        camperApplication.personalData.pesel?.let {
            val alreadyRegistered = campParticipantRepository.existsByPeselAndCampRegistrationsEditionId(
                    camperApplication.personalData.pesel,
                    campRegistrationsEditionId
            )
            if (alreadyRegistered) {
                throw DomainRuleViolationException.of(CAMP_PARTICIPANT_WITH_GIVEN_PESEL_IS_ALREADY_REGISTERED)
            }
        }

        val camperCottage = cottageRepository.findByIdAndSpecification(
                camperApplication.cottageId,
                ActivatedCottageSpecification().and(cottageFreeSpaceSpecificationFactory.createFor(camperApplication))
        ) ?: throw DomainRuleViolationException.of(COTTAGE_NOT_FOUND)

        val campEditionWithInProgressRegistrations = campEditionRepository.findByIdAndSpecification(
                camperCottage.getCampEditionId(),
                InProgressCampRegistrationsSpecification()
        ) ?: throw DomainRuleViolationException.of(CAMP_EDITION_HAS_NOT_IN_PROGRESS_REGISTRATIONS)

        return CampParticipant(
                campParticipantIdGenerator = campParticipantIdGenerator,
                campRegistrationsEditionId = campEditionWithInProgressRegistrations.getAggregateId(),
                currentCamperData = camperApplication,
                stayDuration = stayDuration ?: StayDuration(
                        checkInDate = campEditionWithInProgressRegistrations.getCampEditionStartDate(),
                        checkOutDate = campEditionWithInProgressRegistrations.getCampEditionEndDate()
                )
        )
    }

    fun recreateFrom(campParticipantRegistration: CampParticipantRegistration) =
            createCampParticipant(campParticipantRegistration.getCampRegistrationsEditionId(), campParticipantRegistration.getCamperApplication())
}