package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.dto.toDto
import org.bialydunajec.ddd.application.base.dto.toValueObject
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.BirthDate
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.valueobject.CamperEducation
import org.bialydunajec.registrations.domain.camper.valueobject.CamperPersonalData
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.valueobject.*
import org.bialydunajec.registrations.domain.shirt.valueobject.*

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
        CamperPersonalDataDto(firstName = firstName.toString(), lastName = lastName.toString(), gender = gender, pesel = pesel.toStringOrNull(), birthDate = birthDate.toLocalDate())

fun CamperPersonalDataDto.toValueObject(): CamperPersonalData {
    val pesel = pesel?.let { Pesel(it) }
    return CamperPersonalData(
            firstName = FirstName(firstName),
            lastName = LastName(lastName),
            gender = gender,
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
        ShirtSizeDto(name, type, height, width, length)

fun ShirtSizeDto.toValueObject() =
        ShirtSize(name, type, height, width, length)

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