package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject

enum class Gender : ValueObject {
    FEMALE,
    MALE;

    val isFemale: Boolean
        get() = this == FEMALE

    val isMale: Boolean
        get() = this == MALE
}
