package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.application.base.time.Clock
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampEdition
import org.bialydunajec.registrations.domain.campedition.CampEditionRepository
import org.bialydunajec.registrations.domain.campedition.specification.CampRegistrationsHasMinimumCottagesToStartSpecification
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CreateCampRegistrationsApplicationService(
        private val campEditionRepository: CampEditionRepository
) : ApplicationService<CampRegistrationsCommand.CreateCampRegistrations> {

    override fun process(command: CampRegistrationsCommand.CreateCampRegistrations) {
        val newCampEdition = CampEdition(
                campEditionId = command.campEditionId,
                startDate = command.campEditionStartDate,
                endDate = command.campEditionEndDate
        )
        campEditionRepository.save(newCampEdition)
    }
}

@Service
@Transactional
internal class SetupCampRegistrationsApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.UpdateCampRegistrationsTimer> {

    override fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsTimer) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        campEdition.updateCampRegistrationsTimer(command.timerSettings, clock.currentDateTime())
        campEditionRepository.save(campEdition)
    }
}


@Service
@Transactional
internal class StartCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val cottageRepository: CottageRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.StartCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.StartCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.startNowCampRegistrations(clock.currentDateTime(), CampRegistrationsHasMinimumCottagesToStartSpecification(cottageRepository))

        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional
internal class SuspendCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.SuspendCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.SuspendCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.suspendNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional
internal class UnsuspendCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.UnsuspendCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.UnsuspendCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.unsuspendNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional
internal class FinishCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.FinishCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.FinishCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.finishNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional
internal class CreateAcademicMinistryCottageApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val academicMinistryRepository: AcademicMinistryRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.CreateAcademicMinistryCottage> {

    override fun process(command: CampRegistrationsCommand.CreateAcademicMinistryCottage) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        val academicMinistry = academicMinistryRepository.findById(command.academicMinistryId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.ACADEMIC_MINISTRY_NOT_FOUND)

        val academicMinistryCottage = campEdition.createAcademicMinistryCottage(academicMinistry)

        cottageRepository.save(academicMinistryCottage)
    }
}

@Service
@Transactional
internal class CreateStandaloneCottageApplicationService(
        private val campEditionRepository: CampEditionRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.CreateStandaloneCottage> {

    override fun process(command: CampRegistrationsCommand.CreateStandaloneCottage) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        val academicMinistryCottage = campEdition.createStandaloneCottage(command.cottageName)

        cottageRepository.save(academicMinistryCottage)
    }
}


