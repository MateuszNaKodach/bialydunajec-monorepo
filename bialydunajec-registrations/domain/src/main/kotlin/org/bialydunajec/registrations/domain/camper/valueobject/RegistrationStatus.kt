package org.bialydunajec.registrations.domain.camper.valueobject

enum class RegistrationStatus {
    WAITING_FOR_VERIFICATION,
    VERIFIED_BY_CAMPER,
    CANCELLED_BY_CAMPER,
    VERIFIED_BY_AUTHORIZED,
    CANCELLED_BY_AUTHORIZED,
    CANCELLED_BY_DEADLINE;

    fun isVerified() = this == VERIFIED_BY_CAMPER || this == VERIFIED_BY_AUTHORIZED
    fun isCancelled() = this == CANCELLED_BY_DEADLINE || this == CANCELLED_BY_AUTHORIZED

}