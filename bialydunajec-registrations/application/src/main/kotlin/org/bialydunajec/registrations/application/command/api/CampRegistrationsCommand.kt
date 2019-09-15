package org.bialydunajec.registrations.application.command.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.valueobject.BankTransferDetails
import org.bialydunajec.registrations.domain.cottage.valueobject.CampersLimitations
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageBoss
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSpace
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountId
import org.bialydunajec.registrations.domain.payment.valueobject.PaymentCommitmentType
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId
import org.bialydunajec.registrations.domain.shirt.valueobject.CamperShirtOrder
import org.bialydunajec.registrations.domain.shirt.valueobject.Color
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSize
import java.time.LocalDate
import java.time.ZonedDateTime

sealed class CampRegistrationsCommand : Command {
    internal data class CreateCampRegistrationsEdition(
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val campEditionStartDate: LocalDate,
        val campEditionEndDate: LocalDate,
        val totalPrice: Money,
        val downPaymentAmount: Money?
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
                : this(
            CampRegistrationsEditionId(campRegistrationsEditionId),
            TimerSettings(timerStartDate, timerEndDate)
        )
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
        constructor(campRegistrationsEditionId: Int, academicMinistryId: String) : this(
            CampRegistrationsEditionId(
                campRegistrationsEditionId
            ), AcademicMinistryId(academicMinistryId)
        )
    }

    data class CreateStandaloneCottage(
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val cottageName: String
    ) : CampRegistrationsCommand() {
        constructor(campRegistrationsEditionId: Int, cottageName: String) : this(
            CampRegistrationsEditionId(
                campRegistrationsEditionId
            ), cottageName
        )
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
    ) : CampRegistrationsCommand()

    data class UpdateCottageConditions constructor(
        val cottageId: CottageId,
        val temporaryConditionsDescription: String
    ) : CampRegistrationsCommand()

    data class ActivateCottage constructor(
        val cottageId: CottageId
    ) : CampRegistrationsCommand()

    data class DeactivateCottage constructor(
        val cottageId: CottageId
    ) : CampRegistrationsCommand()

    data class DeleteCottage constructor(
        val cottageId: CottageId
    ) : CampRegistrationsCommand()

    data class RegisterCampParticipantCommand constructor(
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val camperApplication: CamperApplication,
        val shirtOrder: CamperShirtOrder
    ) : CampRegistrationsCommand()

    data class CorrectCampParticipantRegistrationDataCommand constructor(
        val campParticipantId: CampParticipantId,
        val camperApplication: CamperApplication
    ) : CampRegistrationsCommand()

    data class VerifyCampParticipantRegistrationCommand constructor(
        val campParticipantRegistrationId: CampParticipantRegistrationId,
        val verificationCode: String
    ) : CampRegistrationsCommand()

    data class VerifyCampParticipantRegistrationCommandByAuthorized constructor(
        val campParticipantRegistrationId: CampParticipantRegistrationId
    ) : CampRegistrationsCommand()

    data class UnregisterCampParticipantByAuthorizedCommand constructor(
        val campParticipantId: CampParticipantId
    ) : CampRegistrationsCommand()

    data class UpdateCampEditionShirt(
        val campEditionShirtId: CampEditionShirtId,
        val shirtSizesFileUrl: Url?
    ) : CampRegistrationsCommand()

    data class AddCampEditionShirtColorOption(
        val campEditionShirtId: CampEditionShirtId,
        val color: Color,
        val available: Boolean
    ) : CampRegistrationsCommand()

    data class UpdateCampEditionShirtColorOption(
        val campEditionShirtId: CampEditionShirtId,
        val shirtColorOptionId: ShirtColorOptionId,
        val color: Color,
        val available: Boolean
    ) : CampRegistrationsCommand()

    data class RemoveCampEditionShirtColorOption(
        val campEditionShirtId: CampEditionShirtId,
        val shirtColorOptionId: ShirtColorOptionId
    ) : CampRegistrationsCommand()

    data class AddCampEditionShirtSizeOption(
        val campEditionShirtId: CampEditionShirtId,
        val size: ShirtSize,
        val available: Boolean
    ) : CampRegistrationsCommand()

    data class UpdateCampEditionShirtSizeOption(
        val campEditionShirtId: CampEditionShirtId,
        val shirtSizeOptionId: ShirtSizeOptionId,
        val size: ShirtSize,
        val available: Boolean
    ) : CampRegistrationsCommand()

    data class RemoveCampEditionShirtSizeOption(
        val campEditionShirtId: CampEditionShirtId,
        val shirtSizeOptionId: ShirtSizeOptionId
    ) : CampRegistrationsCommand()

    data class PlaceCampEditionShirtOrder(
        val campEditionShirtId: CampEditionShirtId,
        val campParticipantId: CampParticipantId,
        val color: Color,
        val size: ShirtSize
    ) : CampRegistrationsCommand()

    data class DepositMoney(
        val campParticipantCottageAccountId: CampParticipantCottageAccountId,
        val money: Money
    ) : CampRegistrationsCommand()

    data class PayCommitmentAndDepositMoney(
        val campParticipantCottageAccountId: CampParticipantCottageAccountId,
        val type: PaymentCommitmentType
    ) : CampRegistrationsCommand()

    data class PayCommitmentWithAccountFunds(
        val campParticipantCottageAccountId: CampParticipantCottageAccountId,
        val type: PaymentCommitmentType
    ) : CampRegistrationsCommand()
}
