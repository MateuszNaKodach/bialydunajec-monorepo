package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
import java.time.LocalDate

sealed class CampRegistrationsCommand : Command {
    data class CreateCampRegistrations(
            val campEditionId: CampEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampRegistrationsCommand()

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

    data class UnsuspendCampRegistrationsNow(
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