package org.bialydunajec.campedition.application.command.api

import org.bialydunajec.campedition.domain.campedition.CampEditionId
import org.bialydunajec.ddd.application.base.command.Command
import java.time.LocalDate

sealed class CampEditionCommand : Command {
    data class CreateCampEdition internal constructor(
            val campEditionId: CampEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampEditionCommand() {
        companion object {
            fun from(campEditionNumber: Int, campEditionStartDate: LocalDate, campEditionEndDate: LocalDate) =
                    CreateCampEdition(CampEditionId(campEditionNumber), campEditionStartDate, campEditionEndDate)
        }
    }

    data class UpdateCampEditionDuration(
            val campEditionId: CampEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampEditionCommand() {
        companion object {
            fun from(campEditionNumber: Int, campEditionStartDate: LocalDate, campEditionEndDate: LocalDate) =
                    UpdateCampEditionDuration(CampEditionId(campEditionNumber), campEditionStartDate, campEditionEndDate)
        }
    }
}