package org.bialydunajec.registrations.application.query.api.dto

import org.bialydunajec.ddd.application.base.query.api.dto.toDto
import org.bialydunajec.ddd.application.base.query.api.dto.toValueObject
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