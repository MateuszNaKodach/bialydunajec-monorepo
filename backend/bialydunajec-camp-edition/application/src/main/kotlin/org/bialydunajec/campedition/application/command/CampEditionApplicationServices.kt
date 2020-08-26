package org.bialydunajec.campedition.application.command

import org.bialydunajec.campedition.application.command.api.CampEditionCommand
import org.bialydunajec.campedition.domain.campedition.CampEdition
import org.bialydunajec.campedition.domain.campedition.CampEditionRepository
import org.bialydunajec.campedition.domain.exception.CampEditionDomainRule
import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException

//TODO: Add checking for overlapped camp editions by LocalDateRange!!!
internal class CreateCampEditionApplicationService(
        private val campEditionRepository: CampEditionRepository
) : ApplicationService<CampEditionCommand.CreateCampEdition> {

    override fun execute(command: CampEditionCommand.CreateCampEdition) {
        val newCampEdition = CampEdition(
                campEditionId = command.campEditionId,
                startDate = command.campEditionStartDate,
                endDate = command.campEditionEndDate,
                totalPrice = command.campEditionPrice,
                downPaymentAmount = command.campEditionDownPaymentAmount
        )
        campEditionRepository.save(newCampEdition)
    }
}

internal class UpdateCampEditionDurationApplicationService(
        private val campEditionRepository: CampEditionRepository
) : ApplicationService<CampEditionCommand.UpdateCampEditionDuration> {

    override fun execute(command: CampEditionCommand.UpdateCampEditionDuration) {
        val campEdition = campEditionRepository.findById(command.campEditionId)
                ?: throw DomainRuleViolationException.of(CampEditionDomainRule.CAMP_EDITION_TO_UPDATE_MUST_EXISTS)

        campEdition.updateDuration(command.campEditionStartDate, command.campEditionEndDate)
        campEditionRepository.save(campEdition)
    }
}
