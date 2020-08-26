package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.Positive


@Embeddable
class AgeRange(
        @Positive
        val min: Int? = null,
        @Positive
        val max: Int? = null
) : ValueObject
