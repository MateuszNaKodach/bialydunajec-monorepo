package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import java.time.LocalDate
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

@Embeddable
data class BirthDate(
        @NotNull
        @Past
        val birthDate: LocalDate
)