package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.valueobject.BankTransferDetails
import org.bialydunajec.registrations.domain.cottage.valueobject.CampersLimitations
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageBoss
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSpace
import java.time.LocalDate
import java.time.ZonedDateTime

sealed class CampRegistrationsCommand : Command {
    internal data class CreateCampRegistrationsEdition(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampRegistrationsCommand()

    internal data class UpdateCampRegistrationsEditionDuration(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    ) : CampRegistrationsCommand()

    data class UpdateCampRegistrationsTimer(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val timerSettings: TimerSettings
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int, timerStartDate: ZonedDateTime?, timerEndDate: ZonedDateTime?)
                : this(CampRegistrationsEditionId(campRegistrationsEditionId), TimerSettings(timerStartDate, timerEndDate))
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

    data class UpdateCottage constructor(
            val cottageId: CottageId,
            val name: String,
            val logoImageUrl: Url?,
            val buildingPhotoUrl: Url?,
            val place: Place?,
            val cottageSpace: CottageSpace,
            val campersLimitations: CampersLimitations?,
            val bankTransferDetails: BankTransferDetails?,
            val cottageBoss: CottageBoss?
    ) : CampRegistrationsCommand() {

    }

    data class ActivateCottage constructor(
            val cottageId: CottageId
    ) : CampRegistrationsCommand() {

    }

    data class DeactivateCottage constructor(
            val cottageId: CottageId
    ) : CampRegistrationsCommand() {

    }

    data class RegisterCampParticipantCommand constructor(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val camperApplication: CamperApplication
    ) : CampRegistrationsCommand() {

    }

    data class VerifyCampParticipantRegistrationCommand constructor(
            val campParticipantRegistrationId: CampParticipantRegistrationId,
            val verificationCode: String
    ) : CampRegistrationsCommand() {

    }
}