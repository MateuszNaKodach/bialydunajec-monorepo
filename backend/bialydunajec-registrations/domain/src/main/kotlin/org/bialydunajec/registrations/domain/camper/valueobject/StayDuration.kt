package org.bialydunajec.registrations.domain.camper.valueobject

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule.*
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.Embeddable

@Embeddable
data class StayDuration constructor(
        val checkInDate: LocalDate? = null,
        val checkInTime: LocalTime? = null,
        val checkOutDate: LocalDate? = null,
        val checkOutTime: LocalTime? = null
) {

    init {
        if (checkInDate == null && checkInTime != null) {
            throw DomainRuleViolationException.of(CHECK_IN_TIME_CAN_NOT_BE_SPECIFIED_WITHOUT_CHECK_IN_DATE)
        }

        if (checkOutDate == null && checkOutTime != null) {
            throw DomainRuleViolationException.of(CHECK_OUT_TIME_CAN_NOT_BE_SPECIFIED_WITHOUT_CHECK_OUT_DATE)
        }
    }

    fun withCheckInTime(checkInDate: LocalDate, checkInTime: LocalTime) =
            copy(checkInDate = checkInDate, checkInTime = checkInTime)

    fun withoutCheckInTime() =
            copy(checkInTime = null)

    fun withCheckInDate(checkInDate: LocalDate) =
            copy(checkInDate = checkInDate)

    fun withoutCheckInDate() =
            copy(checkInDate = null, checkInTime = null)

    fun withCheckOutTime(checkOutDate: LocalDate, checkOutTime: LocalTime) =
            copy(checkOutDate = checkOutDate, checkOutTime = checkOutTime)

    fun withoutCheckOutTime() =
            copy(checkOutTime = null)

    fun withCheckOutDate(checkOutDate: LocalDate) =
            copy(checkOutDate = checkOutDate)

    fun withoutCheckOutDate() =
            copy(checkOutDate = null, checkOutTime = null)


    companion object {
        fun withCheckInTime(checkInDate: LocalDate, checkInTime: LocalTime) =
                StayDuration(
                        checkInDate = checkInDate,
                        checkInTime = checkInTime
                )

        fun withCheckInDate(checkInDate: LocalDate) =
                StayDuration(
                        checkInDate = checkInDate
                )

        fun withCheckOutTime(checkOutDate: LocalDate, checkOutTime: LocalTime) =
                StayDuration(
                        checkOutDate = checkOutDate,
                        checkOutTime = checkOutTime
                )

        fun withCheckOutDate(checkOutDate: LocalDate) =
                StayDuration(
                        checkOutDate = checkOutDate
                )
    }
}