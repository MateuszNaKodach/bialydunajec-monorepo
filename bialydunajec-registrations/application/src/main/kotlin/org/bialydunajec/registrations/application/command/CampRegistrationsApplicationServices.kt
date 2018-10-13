package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.application.base.time.Clock
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.campedition.specification.CampRegistrationsHasMinimumCottagesToStartSpecification
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
internal class CreateCampRegistrationsEditionApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository
) : ApplicationService<CampRegistrationsCommand.CreateCampRegistrationsEdition> {

    override fun process(command: CampRegistrationsCommand.CreateCampRegistrationsEdition) {
        val newCampEdition = CampRegistrationsEdition(
                campRegistrationsEditionId = command.campRegistrationsEditionId,
                editionStartDate = command.campEditionStartDate,
                editionEndDate = command.campEditionEndDate
        )
        campEditionRepository.save(newCampEdition)
    }
}

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
internal class UpdateCampRegistrationsEditionDurationApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository
) : ApplicationService<CampRegistrationsCommand.UpdateCampRegistrationsEditionDuration> {

    override fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsEditionDuration) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        campEdition.updateCampEditionDuration(command.campEditionStartDate, command.campEditionEndDate)
        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
internal class SetupCampRegistrationsApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.UpdateCampRegistrationsTimer> {

    override fun process(command: CampRegistrationsCommand.UpdateCampRegistrationsTimer) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        campEdition.updateCampRegistrationsTimer(command.timerSettings, clock.currentDateTime())
        campEditionRepository.save(campEdition)
    }
}


@Service
@Transactional
internal class StartCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val cottageRepository: CottageRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.StartCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.StartCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.startNowCampRegistrations(clock.currentDateTime(), CampRegistrationsHasMinimumCottagesToStartSpecification(cottageRepository))

        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional
internal class SuspendCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.SuspendCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.SuspendCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.suspendNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional
internal class UnsuspendCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.UnsuspendCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.UnsuspendCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.unsuspendNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional
internal class FinishCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.FinishCampRegistrationsNow> {

    override fun process(command: CampRegistrationsCommand.FinishCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.finishNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

@Service
@Transactional
internal class CreateAcademicMinistryCottageApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        @Qualifier("cottageAcademicMinistryRepositoryImpl")
        private val academicMinistryRepository: AcademicMinistryRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.CreateAcademicMinistryCottage> {

    override fun process(command: CampRegistrationsCommand.CreateAcademicMinistryCottage): CottageId {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        val academicMinistry = academicMinistryRepository.findById(command.academicMinistryId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.ACADEMIC_MINISTRY_NOT_FOUND)

        val academicMinistryCottage = campEdition.createAcademicMinistryCottage(academicMinistry)

        return cottageRepository.save(academicMinistryCottage).getAggregateId()
    }
}

@Service
@Transactional
internal class CreateStandaloneCottageApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.CreateStandaloneCottage> {

    override fun process(command: CampRegistrationsCommand.CreateStandaloneCottage): CottageId {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        val academicMinistryCottage = campEdition.createStandaloneCottage(command.cottageName)

        return cottageRepository.save(academicMinistryCottage).getAggregateId()
    }
}

@Service
@Transactional
internal class UpdateCottageApplicationService(
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.UpdateCottage> {

    override fun process(command: CampRegistrationsCommand.UpdateCottage) {
        val cottage = cottageRepository.findById(command.cottageId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.COTTAGE_NOT_FOUND)

        cottage.update(
                command.name,
                command.logoImageUrl,
                command.buildingPhotoUrl,
                command.place,
                command.cottageSpace,
                command.campersLimitations,
                command.bankTransferDetails
        )

        cottageRepository.save(cottage)
    }
}

@Service
@Transactional
internal class ActivateCottageApplicationService(
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.ActivateCottage> {

    override fun process(command: CampRegistrationsCommand.ActivateCottage) {
        val cottage = cottageRepository.findById(command.cottageId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.COTTAGE_NOT_FOUND)
        cottage.activate()
        cottageRepository.save(cottage)
    }
}

@Service
@Transactional
internal class DeactivateCottageApplicationService(
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.DeactivateCottage> {

    override fun process(command: CampRegistrationsCommand.DeactivateCottage) {
        val cottage = cottageRepository.findById(command.cottageId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.COTTAGE_NOT_FOUND)
        cottage.deactivate()
        cottageRepository.save(cottage)
    }
}
