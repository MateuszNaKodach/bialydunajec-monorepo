package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.exception.DomainException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.CampEditionRepository
import org.bialydunajec.registrations.domain.camper.valueobject.CamperEducation
import org.bialydunajec.registrations.domain.camper.valueobject.CamperPersonalData
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsRepository
import org.bialydunajec.registrations.domain.campregistrations.specification.InProgressCampRegistrationsSpecification
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.exception.CampersRegisterDomainErrorCode.*

//TODO: Dodać metode sprawdzajaca czy pesel sie nie powtarza, tworzenie id z hashowaniem peselu BCrypt - dla trzymania danych archiwalnych, ew. random!
//TODO: Czy chatka wolna sprawdzać na etapie aplikacji!
class CamperFactory(
        val cottageRepository: CottageRepository,
        val campRegistrationsRepository: CampRegistrationsRepository,
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
        val camperCottage = cottageRepository.findById(cottageId) ?: throw DomainException.of(COTTAGE_NOT_FOUND)

        val cottageInProgressCampRegistrations = campRegistrationsRepository.findByIdAndSpecification(
                camperCottage.getCampRegistrationsId(),
                InProgressCampRegistrationsSpecification()
        ) ?: throw DomainException.of(SELECTED_CAMP_REGISTRATIONS_IS_NOT_IN_PROGRESS)

        val campRegistrationsEdition = campEditionRepository.findById(cottageInProgressCampRegistrations.getCampEditionId())
                ?: throw DomainException.of(CAMP_EDITION_NOT_FOUND)

        val campEditionDuration = campRegistrationsEdition.getCampEditionDuration()

        return Camper(
                cottageId = cottageId,
                campRegistrationsId = cottageInProgressCampRegistrations.getAggregateId(),
                personalData = personalData,
                homeAddress = homeAddress,
                emailAddress = emailAddress,
                phoneNumber = phoneNumber,
                camperEducation = camperEducation,
                stayDuration = stayDuration ?: StayDuration(
                        checkInDate = campEditionDuration.getStartDate(),
                        checkOutDate = campEditionDuration.getEndDate()
                )
        )
    }

}