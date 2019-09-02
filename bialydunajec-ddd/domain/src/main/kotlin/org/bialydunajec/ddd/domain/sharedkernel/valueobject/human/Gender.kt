package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject

enum class Gender : ValueObject {
    FEMALE,
    MALE;

    private val isFemale: Boolean
        get() = this == FEMALE

    private val isMale: Boolean
        get() = this == MALE
}