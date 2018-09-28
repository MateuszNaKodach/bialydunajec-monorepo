package org.bialydunajec.registrations.application

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.exception.DomainException
import org.bialydunajec.registrations.application.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampEditionRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.exception.CampersRegisterDomainErrorCode
import org.springframework.stereotype.Service

@Service
internal class SetupCampRegistrationsApplicationService(
        private val campEditionRepository: CampEditionRepository
) : ApplicationService<CampRegistrationsCommand.SetupCampRegistrations> {

    override fun process(command: CampRegistrationsCommand.SetupCampRegistrations) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainException.of(CampersRegisterDomainErrorCode.CAMP_EDITION_NOT_FOUND)
        campEdition.updateCampRegistrationsDuration(command.registrationsDuration)
        campEditionRepository.save(campEdition)
    }
}


@Service
internal class StartCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.StartCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.StartCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainException.of(CampersRegisterDomainErrorCode.CAMP_EDITION_NOT_FOUND)

        val academicMinistryCottage = campEdition.startCampRegistrations()

        cottageRepository.save(academicMinistryCottage)
    }
}

@Service
internal class CreateAcademicMinistryCottageApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val academicMinistryRepository: AcademicMinistryRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.CreateAcademicMinistryCottage> {

    override fun process(command: CampRegistrationsCommand.CreateAcademicMinistryCottage) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainException.of(CampersRegisterDomainErrorCode.CAMP_EDITION_NOT_FOUND)
        val academicMinistry = academicMinistryRepository.findById(command.academicMinistryId)
                ?: throw DomainException.of(CampersRegisterDomainErrorCode.ACADEMIC_MINISTRY_NOT_FOUND)

        val academicMinistryCottage = campEdition.createAcademicMinistryCottage(academicMinistry)

        cottageRepository.save(academicMinistryCottage)
    }
}

@Service
internal class CreateStandaloneCottageApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.CreateStandaloneCottage> {

    override fun process(command: CampRegistrationsCommand.CreateStandaloneCottage) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainException.of(CampersRegisterDomainErrorCode.CAMP_EDITION_NOT_FOUND)

        val academicMinistryCottage = campEdition.createStandaloneCottage(command.cottageName)

        cottageRepository.save(academicMinistryCottage)
    }
}


