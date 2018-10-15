package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.application.base.query.api.dto.toDto
import org.bialydunajec.ddd.application.base.query.api.dto.toValueObject
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.BirthDate
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.domain.camper.valueobject.CamperEducation
import org.bialydunajec.registrations.domain.camper.valueobject.CamperPersonalData
import org.bialydunajec.registrations.domain.cottage.valueobject.BankTransferDetails
import org.bialydunajec.registrations.domain.cottage.valueobject.CampersLimitations
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSpace

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

fun CamperPersonalDataDto.toValueObject() =
        CamperPersonalData(
                firstName = FirstName(firstName),
                lastName = LastName(lastName),
                gender = gender,
                pesel = pesel?.let { Pesel(it) },
                birthDate = BirthDate(birthDate)
        )

fun CamperEducation.toDto() =
        CamperEducationDto(university, faculty, fieldOfStudy, highSchool, isHighSchoolRecentGraduate)

fun CamperEducationDto.toValueObject() =
        CamperEducation(university, faculty, fieldOfStudy, highSchool, isHighSchoolRecentGraduate)