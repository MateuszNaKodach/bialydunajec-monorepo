package org.bialydunajec.campedition.application.command.api

import org.bialydunajec.campedition.domain.campedition.CampEditionId
import org.bialydunajec.ddd.application.base.command.Command
import java.time.LocalDate

sealed class CampEditionCommand : Command {
    data class CreateCampEdition(
            val campEditionId: CampEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampEditionCommand()

    data class UpdateCampEditionDuration(
            val campEditionId: CampEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampEditionCommand()
}