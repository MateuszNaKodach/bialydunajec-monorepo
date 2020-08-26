package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.dto.toDto
import org.bialydunajec.ddd.application.base.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.extensions.toStringOrNull
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.*
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.academicministry.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.registrations.domain.campedition.valueobject.CampRegistrationsEditionSnapshot
import org.bialydunajec.registrations.domain.campedition.valueobject.CampRegistrationsSnapshot
import org.bialydunajec.registrations.domain.camper.valueobject.*
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.valueobject.*
import org.bialydunajec.registrations.domain.shirt.valueobject.*
import org.bialydunajec.registrations.dto.*

fun ShirtType.toDto() =
        ShirtTypeDto.values().find { it.name == name }!!

fun ShirtTypeDto.toValueObject() =
        ShirtType.values().find { it.name == name }!!

fun ParticipationStatus.toDto() =
        ParticipationStatusDto.values().find { it.name == name }!!

fun ParticipationStatusDto.toValueObject() =
        ParticipationStatus.values().find { it.name == name }!!


fun CottageSpace.toDto() =
        CottageSpaceDto(
                fullCapacity,
                reservations,
                maxFemaleTotal,
                maxMaleTotal,
                highSchoolRecentGraduatesCapacity,
                maxFemaleHighSchoolRecentGraduates,
                maxMaleHighSchoolRecentGraduates
        )

fun CottageSpaceDto.toValueObject() =
        CottageSpace(
                fullCapacity,
                reservations,
                maxFemaleTotal,
                maxMaleTotal,
                highSchoolRecentGraduatesCapacity,
                maxFemaleHighSchoolRecentGraduates,
                maxMaleHighSchoolRecentGraduates
        )

fun CampersLimitations.toDto() =
        CampersLimitationsDto(
                ageRange?.toDto()
        )

fun CampersLimitationsDto.toValueObject() =
        CampersLimitations(
                ageRange?.toValueObject()
        )

fun BankTransferDetails.toDto() =
        BankTransferDetailsDto(
                accountNumber,
                accountOwner,
                accountOwnerAddress,
                transferTitleTemplate
        )

fun BankTransferDetailsDto.toValueObject() =
        BankTransferDetails(
                accountNumber, accountOwner, accountOwnerAddress, transferTitleTemplate
        )

fun CamperPersonalData.toDto() =
        CamperPersonalDataDto(firstName = firstName.toString(), lastName = lastName.toString(), gender = gender.toDto(), pesel = pesel.toStringOrNull(), birthDate = birthDate?.toLocalDate())

fun CamperPersonalDataDto.toValueObject(): CamperPersonalData {
    val pesel = pesel?.let { Pesel(it) }
    return CamperPersonalData(
            firstName = FirstName(firstName),
            lastName = LastName(lastName),
            gender = gender.toValueObject(),
            pesel = pesel,
            birthDate = birthDate?.let { BirthDate(it) }
                    ?: pesel!!.getBirthDate() // TODO: Add validation for DTO - PESEL or BirthDate
    )
}

fun CamperEducation.toDto() =
        CamperEducationDto(university, faculty, fieldOfStudy, highSchool, isHighSchoolRecentGraduate)

fun CamperEducationDto.toValueObject() =
        CamperEducation(university, faculty, fieldOfStudy, highSchool, isHighSchoolRecentGraduate)

fun CamperApplication.toDto() =
        CamperApplicationDto(
                cottageId.toString(),
                personalData.toDto(),
                homeAddress.toDto(),
                phoneNumber.toString(),
                emailAddress.toString(),
                camperEducation.toDto()
        )

fun CamperApplication.toDtoWithCottage(cottage: CottageSnapshot) =
        CamperApplicationWithCottageDto(
                CamperApplicationCottageDto(
                        cottage.cottageId.toString(),
                        cottage.name
                ),
                personalData.toDto(),
                homeAddress.toDto(),
                phoneNumber.toString(),
                emailAddress.toString(),
                camperEducation.toDto()
        )

fun CamperApplicationDto.toValueObject() =
        CamperApplication(
                CottageId(cottageId),
                personalData.toValueObject(),
                homeAddress.toValueObject(),
                EmailAddress(emailAddress),
                PhoneNumber(phoneNumber),
                camperEducation.toValueObject()
        )

fun StayDuration.toDto() =
        StayDurationDto(checkInDate, checkInTime, checkOutDate, checkOutTime)

fun StayDurationDto.toValueObject() =
        StayDuration(checkInDate, checkInTime, checkOutDate, checkOutTime)

fun CottageBoss.toDto() =
        CottageBossDto(
                firstName.toStringOrNull(),
                lastName.toStringOrNull(),
                phoneNumber.toStringOrNull(),
                emailAddress.toStringOrNull(),
                university.toStringOrNull(),
                fieldOfStudy.toStringOrNull(),
                photoUrl.toStringOrNull(),
                personalDescription?.toDto()
        )

fun CottageBossDto.toValueObject() =
        CottageBoss(
                firstName?.let { FirstName(it) },
                lastName?.let { LastName(it) },
                phoneNumber?.let { PhoneNumber(it) },
                emailAddress?.let { EmailAddress(it) },
                university.toStringOrNull(),
                fieldOfStudy.toStringOrNull(),
                photoUrl?.let { Url.ExternalUrl(it) },
                personalDescription?.toValueObject()
        )

fun ShirtSize.toDto() =
        ShirtSizeDto(name, type.toDto(), height, width, length)

fun ShirtSizeDto.toValueObject() =
        ShirtSize(name, type.toValueObject(), height, width, length)

fun ShirtSizeOptionSnapshot.toDto() =
        ShirtSizeOptionDto(
                shirtSizeOptionId.toString(),
                size.toDto(),
                available
        )

fun Color.toDto() =
        ColorDto(name, hexValue)

fun ColorDto.toValueObject() =
        Color(name, hexValue)

fun ShirtColorOptionSnapshot.toDto() =
        ShirtColorOptionDto(shirtColorOptionId.toString(), color.toDto(), available)

fun CampEditionShirtSnapshot.toDto() =
        CampEditionShirtDto(
                campEditionShirtId.toString(),
                campRegistrationsEditionId.toString(),
                shirtSizesFileUrl.toStringOrNull(),
                colorOptions.map { it.toDto() },
                sizeOptions.map { it.toDto() }
        )


fun AcademicMinistrySnapshot.toDto() =
        AcademicMinistryDto(
                academicMinistryId = academicMinistryId.toString(),
                officialName = officialName,
                shortName = shortName,
                logoImageUrl = logoImageUrl.toStringOrNull()
        )


fun CampRegistrationsEditionSnapshot.toDto() =
        CampEditionDto(
                campEditionId = campRegistrationsEditionId.toString(),
                campEditionStartDate = editionStartDate,
                campEditionEndDate = editionEndDate,
                campEditionYear = editionStartDate.year,
                editionPrice = editionPrice.getValue().toDouble(),
                editionDownPaymentAmount = editionDownPaymentAmount?.getValue()?.toDouble()
        )

fun CampParticipantDto.Companion.from(snapshot: CampParticipantSnapshot,
                                                                         confirmedCottage: CottageSnapshot?,
                                                                         currentCottage: CottageSnapshot) =
        with(snapshot) {
            CampParticipantDto(
                    campParticipantId.toString(),
                    campRegistrationsEditionId.toString(),
                    confirmedCottage?.let { confirmedApplication?.toDtoWithCottage(confirmedCottage) },
                    currentCamperData.toDtoWithCottage(currentCottage),
                    stayDuration.toDto(),
                    participationStatus.toDto()
            )
        }

fun CampRegistrationsCottageDto.Companion.from(snapshot: CottageSnapshot, hasSpace: Boolean) =
        CampRegistrationsCottageDto(
                cottageId = snapshot.cottageId.toString(),
                cottageType = snapshot.cottageType.toString(),
                academicMinistryId = snapshot.academicMinistryId.toStringOrNull(),
                name = snapshot.name,
                logoImageUrl = snapshot.logoImageUrl.toStringOrNull(),
                buildingPhotoUrl = snapshot.buildingPhotoUrl.toStringOrNull(),
                place = snapshot.place?.toDto(),
                hasSpace = hasSpace
        )

fun CampRegistrationsEditionSnapshot.toCampRegistrationsEditionDto() =
        CampRegistrationsEditionDto(
                campRegistrationsEditionId = campRegistrationsEditionId.toString(),
                editionStartDate = editionStartDate,
                editionEndDate = editionEndDate,
                editionPrice = editionPrice.getValue().toDouble(),
                campRegistrations = campRegistrations.toDto(),
                editionDownPaymentAmount = editionDownPaymentAmount?.getValue()?.toDouble()
        )

fun CottageSnapshot.toDto() =
        CottageDto(
                cottageId = cottageId.toString(),
                campRegistrationsEditionId = campRegistrationsEditionId.toString(),
                cottageType = cottageType.toString(),
                academicMinistryId = academicMinistryId.toStringOrNull(),
                name = name,
                logoImageUrl = logoImageUrl.toStringOrNull(),
                buildingPhotoUrl = buildingPhotoUrl.toStringOrNull(),
                place = place?.toDto(),
                cottageSpace = cottageSpace?.toDto(),
                campersLimitations = campersLimitations?.toDto(),
                bankTransferDetails = bankTransferDetails?.toDto(),
                cottageState = cottageState.toString(),
                cottageBoss = cottageBoss?.toDto()
        )


fun CampRegistrationsSnapshot.toDto() =
        CampRegistrationsDto(
                campRegistrationsId = campRegistrationsId.toString(),
                status = status.name,
                timerStartDate = timerSettings?.startDate,
                timerEndDate = timerSettings?.endDate,
                lastStartedAt = lastStartedAt,
                lastSuspendAt = lastSuspendAt,
                lastUnsuspendAt = lastUnsuspendAt,
                lastFinishedAt = lastFinishedAt
        )

fun CottageSnapshot.toCottageInfoDto() =
        CottageInfoDto(
                cottageId = cottageId.toString(),
                campRegistrationsEditionId = campRegistrationsEditionId.toString(),
                cottageType = cottageType.toString(),
                academicMinistryId = academicMinistryId.toStringOrNull(),
                name = name,
                logoImageUrl = logoImageUrl.toStringOrNull(),
                buildingPhotoUrl = buildingPhotoUrl.toStringOrNull(),
                place = place?.toDto(),
                cottageState = cottageState.toString(),
                cottageBoss = cottageBoss?.toDto(),
                audit = audit.toDto()
        )

