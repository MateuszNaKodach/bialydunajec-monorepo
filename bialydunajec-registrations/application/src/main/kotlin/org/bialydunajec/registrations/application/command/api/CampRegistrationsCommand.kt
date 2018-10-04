package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
import java.time.LocalDate
import java.time.ZonedDateTime

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
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int, startDate: ZonedDateTime?, endDate: ZonedDateTime?)
                : this(CampRegistrationsEditionId(campRegistrationsEditionId), TimerSettings(startDate, endDate))
    }

    data class StartCampRegistrationsNow(
            val campRegistrationsEditionId: CampRegistrationsEditionId
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int) : this(CampRegistrationsEditionId(campRegistrationsEditionId))
    }

    data class FinishCampRegistrationsNow(
            val campRegistrationsEditionId: CampRegistrationsEditionId
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int) : this(CampRegistrationsEditionId(campRegistrationsEditionId))
    }

    data class SuspendCampRegistrationsNow(
            val campRegistrationsEditionId: CampRegistrationsEditionId
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int) : this(CampRegistrationsEditionId(campRegistrationsEditionId))
    }

    data class UnsuspendCampRegistrationsNow(
            val campRegistrationsEditionId: CampRegistrationsEditionId
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int) : this(CampRegistrationsEditionId(campRegistrationsEditionId))
    }

    data class CreateAcademicMinistryCottage(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val academicMinistryId: AcademicMinistryId
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int, academicMinistryId: String) : this(CampRegistrationsEditionId(campRegistrationsEditionId), AcademicMinistryId(academicMinistryId))
    }

    data class CreateStandaloneCottage(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val cottageName: String
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int, cottageName: String) : this(CampRegistrationsEditionId(campRegistrationsEditionId), cottageName)

    }
}