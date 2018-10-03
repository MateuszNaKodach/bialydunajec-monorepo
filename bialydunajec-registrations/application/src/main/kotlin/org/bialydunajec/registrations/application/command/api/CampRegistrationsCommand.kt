package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
import java.time.LocalDate

sealed class CampRegistrationsCommand : Command {
    data class CreateCampRegistrationsEdition(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampRegistrationsCommand()

    data class UpdateCampRegistrationsEditionDuration(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampRegistrationsCommand()

    data class UpdateCampRegistrationsTimer(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val timerSettings: TimerSettings
    ) : CampRegistrationsCommand()

    data class StartCampRegistrationsNow(
            val campRegistrationsEditionId: CampRegistrationsEditionId
    ) : CampRegistrationsCommand()

    data class FinishCampRegistrationsNow(
            val campRegistrationsEditionId: CampRegistrationsEditionId
    ) : CampRegistrationsCommand()

    data class SuspendCampRegistrationsNow(
            val campRegistrationsEditionId: CampRegistrationsEditionId
    ) : CampRegistrationsCommand()

    data class UnsuspendCampRegistrationsNow(
            val campRegistrationsEditionId: CampRegistrationsEditionId
    ) : CampRegistrationsCommand()

    data class CreateAcademicMinistryCottage(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val academicMinistryId: AcademicMinistryId
    ) : CampRegistrationsCommand()

    data class CreateStandaloneCottage(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val cottageName: String
    ) : CampRegistrationsCommand()
}