package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import org.bialydunajec.registrations.domain.campedition.CampEditionRepository
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.valueobject.CamperEducation
import org.bialydunajec.registrations.domain.camper.valueobject.CamperPersonalData
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.campedition.specification.InProgressCampRegistrationsSpecification
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule.*

//TODO: Id Campera jest zwiazane z CampRegistrations
//TODO: Dodać metode sprawdzajaca czy pesel sie nie powtarza, tworzenie id z hashowaniem peselu BCrypt - dla trzymania danych archiwalnych, ew. random!
//TODO: Czy chatka wolna sprawdzać na etapie aplikacji!
internal class CamperFactory(
        val cottageRepository: CottageRepository,
        val campEditionRepository: CampEditionRepository
) {
    fun createCamper(
            cottageId: CottageId,
            personalData: CamperPersonalData,
            homeAddress: Address,
            emailAddress: EmailAddress,
            phoneNumber: PhoneNumber,
            camperEducation: CamperEducation,
            stayDuration: StayDuration? = null
    ): Camper {
        val camperCottage = cottageRepository.findById(cottageId) ?: throw DomainRuleViolationException.of(COTTAGE_NOT_FOUND)

        val campEditionWithInProgressRegistrations = campEditionRepository.findByIdAndSpecification(
                camperCottage.getCampEditionId(),
                InProgressCampRegistrationsSpecification()
        ) ?: throw DomainRuleViolationException.of(CAMP_EDITION_HAS_NOT_IN_PROGRESS_REGISTRATIONS)


        return Camper(
                cottageId = cottageId,
                campEditionId = campEditionWithInProgressRegistrations.getAggregateId(),
                camperApplication = CamperApplication(
                        personalData = personalData,
                        homeAddress = homeAddress,
                        emailAddress = emailAddress,
                        phoneNumber = phoneNumber,
                        camperEducation = camperEducation
                ),
                stayDuration = stayDuration ?: StayDuration(
                        checkInDate = campEditionWithInProgressRegistrations.getCampEditionStartDate(),
                        checkOutDate = campEditionWithInProgressRegistrations.getCampEditionEndDate()
                )
        )
    }

}