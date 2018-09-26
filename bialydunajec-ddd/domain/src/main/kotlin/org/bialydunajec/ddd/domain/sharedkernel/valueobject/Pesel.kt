package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.bialydunajec.ddd.domain.extensions.isEven
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.pl.PESEL
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

    fun getGender() = if (pesel[GENDER_DIGIT_INDEX].toInt().isEven()) Gender.FEMALE else Gender.MALE

    override fun toString() = pesel
}