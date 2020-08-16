package org.bialydunajec.academicministry.application.command

import org.bialydunajec.academicministry.application.command.api.AcademicMinistryCommand
import org.bialydunajec.academicministry.domain.AcademicMinistry
import org.bialydunajec.academicministry.domain.AcademicMinistryRepository
import org.bialydunajec.academicministry.domain.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.academicministry.domain.exception.AcademicMinistryDomainRule
import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CreateAcademicMinistryApplicationService(
        private val academicMinistryRepository: AcademicMinistryRepository
) : ApplicationService<AcademicMinistryCommand.CreateAcademicMinistry> {

    override fun execute(command: AcademicMinistryCommand.CreateAcademicMinistry) =
            AcademicMinistry(
                    officialName = command.officialName,
                    shortName = command.shortName,
                    logoImageUrl = command.logoImageUrl,
                    place = command.place,
                    socialMedia = command.socialMedia,
                    emailAddress = command.emailAddress,
                    photoUrl = command.photoUrl,
                    description = command.description)
                    .let { academicMinistryRepository.save(it) }
                    .getAggregateId()
}

@Service
@Transactional
internal class UpdateAcademicMinistryApplicationService(
        private val academicMinistryRepository: AcademicMinistryRepository
) : ApplicationService<AcademicMinistryCommand.UpdateAcademicMinistry> {

    override fun execute(command: AcademicMinistryCommand.UpdateAcademicMinistry) {
        val academicMinistry = academicMinistryRepository.findById(command.academicMinistryId)
                ?: throw DomainRuleViolationException.of(AcademicMinistryDomainRule.ACADEMIC_MINISTRY_TO_UPDATE_MUST_EXISTS)
        val academicMinistryUpdate = AcademicMinistrySnapshot(
                academicMinistryId = command.academicMinistryId,
                officialName = command.officialName,
                shortName = command.shortName,
                logoImageUrl = command.logoImageUrl,
                place = command.place,
                socialMedia = command.socialMedia,
                emailAddress = command.emailAddress,
                photoUrl = command.photoUrl,
                description = command.description
        )
        academicMinistry.updateWith(academicMinistryUpdate)
        academicMinistryRepository.save(academicMinistry)
    }
}

@Transactional
@Service
internal class CreateAcademicMinistryPriestApplicationService(
        private val academicMinistryRepository: AcademicMinistryRepository
) : ApplicationService<AcademicMinistryCommand.CreateAcademicMinistryPriest> {

    override fun execute(command: AcademicMinistryCommand.CreateAcademicMinistryPriest) {
        val academicMinistry = academicMinistryRepository.findById(command.academicMinistryId)
                ?: throw DomainRuleViolationException.of(AcademicMinistryDomainRule.ACADEMIC_MINISTRY_FOR_PRIEST_MUST_EXISTS)

        with(command) {
            academicMinistry.addNewPriest(
                    firstName,
                    lastName,
                    personalTitle,
                    emailAddress,
                    phoneNumber,
                    description,
                    photoUrl
            )
        }
        academicMinistryRepository.save(academicMinistry)
    }
}

@Transactional
@Service
internal class RemoveAcademicMinistryPriestApplicationService(
        private val academicMinistryRepository: AcademicMinistryRepository
) : ApplicationService<AcademicMinistryCommand.RemoveAcademicMinistryPriest> {

    override fun execute(command: AcademicMinistryCommand.RemoveAcademicMinistryPriest) {
        val academicMinistry = academicMinistryRepository.findById(command.academicMinistryId)
                ?: throw DomainRuleViolationException.of(AcademicMinistryDomainRule.ACADEMIC_MINISTRY_FOR_PRIEST_MUST_EXISTS)

        academicMinistry.removePriest(command.academicPriestId)
        academicMinistryRepository.save(academicMinistry)
    }
}