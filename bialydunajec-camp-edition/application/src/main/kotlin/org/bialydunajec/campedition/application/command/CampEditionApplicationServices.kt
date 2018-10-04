package org.bialydunajec.campedition.application.command

import org.bialydunajec.campedition.application.command.api.CampEditionCommand
import org.bialydunajec.campedition.domain.campedition.CampEdition
import org.bialydunajec.campedition.domain.campedition.CampEditionRepository
import org.bialydunajec.campedition.domain.exception.CampEditionDomainRule
import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
internal class CreateCampEditionApplicationService(
        private val campEditionRepository: CampEditionRepository
) : ApplicationService<CampEditionCommand.CreateCampEdition> {

    override fun process(command: CampEditionCommand.CreateCampEdition) {
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
internal class UpdateCampEditionDurationApplicationService(
        private val campEditionRepository: CampEditionRepository
) : ApplicationService<CampEditionCommand.UpdateCampEditionDuration> {

    override fun process(command: CampEditionCommand.UpdateCampEditionDuration) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampEditionDomainRule.CAMP_EDITION_TO_UPDATE_MUST_EXISTS)

        campEdition.updateDuration(command.campEditionStartDate, command.campEditionEndDate)
        campEditionRepository.save(campEdition)
    }
}