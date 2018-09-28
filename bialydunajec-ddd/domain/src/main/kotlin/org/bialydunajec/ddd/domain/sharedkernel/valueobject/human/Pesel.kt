package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import org.bialydunajec.ddd.domain.base.exception.BusinessRuleViolationException
import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.bialydunajec.ddd.domain.extensions.isEven
import org.bialydunajec.ddd.domain.sharedkernel.exception.SharedKernelDomainErrorCode
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.pl.PESEL
import java.time.LocalDate
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

private const val GENDER_DIGIT_INDEX = 9;
private const val PESEL_LENGTH = 11;

@Embeddable
data class Pesel(
        @Length(min = PESEL_LENGTH, max = PESEL_LENGTH)
        @NotNull
        @PESEL
        private val pesel: String
) : ValueObject {

    init {
        if (!PeselValidator(pesel).isValid) {
            throw BusinessRuleViolationException.of(SharedKernelDomainErrorCode.PESEL_NUMBER_IS_NOT_VALID)
        }
    }

    fun getGender() = if (pesel[GENDER_DIGIT_INDEX].toInt().isEven()) Gender.FEMALE else Gender.MALE

    fun getBirthDate(): BirthDate {
        val peselValidator = PeselValidator(pesel)
        return BirthDate(LocalDate.of(peselValidator.birthYear, peselValidator.birthMonth, peselValidator.birthDay))
    }

    override fun toString() = pesel
}


//TODO: To refactor!
private class PeselValidator(PESELNumber: String) {

    private val PESEL = ByteArray(11)
    var isValid = false
        private set

    val birthYear: Int
        get() {
            var year: Int = 10 * PESEL[0]
            var month: Int = 10 * PESEL[2]
            year += PESEL[1].toInt()
            month += PESEL[3].toInt()
            when (month) {
                in 81..92 -> year += 1800
                in 1..12 -> year += 1900
                in 21..32 -> year += 2000
                in 41..52 -> year += 2100
                in 61..72 -> year += 2200
            }
            return year
        }

    val birthMonth: Int
        get() {
            var month: Int = 10 * PESEL[2]
            month += PESEL[3].toInt()
            when (month) {
                in 81..92 -> month -= 80
                in 21..32 -> month -= 20
                in 41..52 -> month -= 40
                in 61..72 -> month -= 60
            }
            return month
        }


    val birthDay: Int
        get() {
            var day: Int = 10 * PESEL[4]
            day += PESEL[5].toInt()
            return day
        }

    val sex: Gender
        get() =
            if (PESEL[9] % 2 == 1) {
                Gender.MALE
            } else {
                Gender.FEMALE
            }


    init {
        if (PESELNumber.length != 11) {
            isValid = false
        } else {
            for (i in 0..10) {
                PESEL[i] = java.lang.Byte.parseByte(PESELNumber.substring(i, i + 1))
            }
            isValid = checkSum() && checkMonth() && checkDay()
        }
    }

    private fun checkSum(): Boolean {
        var sum = 1 * PESEL[0] +
                3 * PESEL[1] +
                7 * PESEL[2] +
                9 * PESEL[3] +
                1 * PESEL[4] +
                3 * PESEL[5] +
                7 * PESEL[6] +
                9 * PESEL[7] +
                1 * PESEL[8] +
                3 * PESEL[9]
        sum %= 10
        sum = 10 - sum
        sum %= 10

        return sum == PESEL[10].toInt()
    }

    private fun checkMonth(): Boolean {
        val month = birthMonth
        val day = birthDay
        return month in 1..12
    }

    private fun checkDay(): Boolean {
        val year = birthYear
        val month = birthMonth
        val day = birthDay
        return if (day in 1..31 && (month == 1 || month == 3 || month == 5 ||
                        month == 7 || month == 8 || month == 10 ||
                        month == 12)) {
            true
        } else if (day in 1..30 && (month == 4 || month == 6 || month == 9 ||
                        month == 11)) {
            true
        } else day in 1..29 && leapYear(year) || day in 1..28 && !leapYear(year)
    }

    private fun leapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }
}