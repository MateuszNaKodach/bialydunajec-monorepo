package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable

@Embeddable
data class PersonalTitle(
        val name: String? = null,

        val prefix: String? = null,

        val postfix: String? = null
): ValueObject
