package org.bialydunajec.ddd.domain.sharedkernel.valueobject

enum class Gender {
    FEMALE,
    MALE;

    val isFemale: Boolean
        get() = this == FEMALE

    val isMale: Boolean
        get() = this == MALE
}