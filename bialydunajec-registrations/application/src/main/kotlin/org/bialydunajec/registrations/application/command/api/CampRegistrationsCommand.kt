package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.Command
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings

sealed class CampRegistrationsCommand : Command {
    data class UpdateCampRegistrationsTimer(
            val campEditionId: CampEditionId,
            val timerSettings: TimerSettings
    ) : CampRegistrationsCommand()

    data class StartCampRegistrationsNow(
            val campEditionId: CampEditionId
    ) : CampRegistrationsCommand()

    data class FinishCampRegistrationsNow(
            val campEditionId: CampEditionId
    ) : CampRegistrationsCommand()

    data class SuspendCampRegistrationsNow(
            val campEditionId: CampEditionId
    ) : CampRegistrationsCommand()

    data class CreateAcademicMinistryCottage(
            val campEditionId: CampEditionId,
            val academicMinistryId: AcademicMinistryId
    ) : CampRegistrationsCommand()

    data class CreateStandaloneCottage(
            val campEditionId: CampEditionId,
            val cottageName: String
    ) : CampRegistrationsCommand()
}