package org.bialydunajec.registrations.domain.campedition.valueobject

internal enum class RegistrationsStatus {
    /**
     * Lack of necessary configuration
     */
    UNCONFIGURED,
    /**
     * All necessary configuration is setup
     */
    CONFIGURED,
    /**
     * Registrations startDate reached or activated manually
     */
    IN_PROGRESS,
    /**
     * CONFIGURED Registrations can't be deactivated - startDate doesn't matter
     */
    DEACTIVATED,
    /**
     * Registrations are FINISHED when endDate is reached
     */
    FINISHED;

}