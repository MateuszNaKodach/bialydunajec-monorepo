package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import java.time.LocalDate
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

@Embeddable
data class BirthDate(
        @NotNull
        @Past
        private val birthDate: LocalDate
): ValueObject{
        fun toLocalDate() = birthDate
}