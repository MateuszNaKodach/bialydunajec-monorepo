package org.bialydunajec.registrations.domain.campedition.valueobject

enum class RegistrationsStatus {
    /**
     * Lack of necessary configuration
     */
    UNCONFIGURED_TIMER,
    /**
     * All necessary configuration is setup
     */
    CONFIGURED_TIMER,
    /**
     * Registrations startDate reached or activated manually
     */
    IN_PROGRESS,
    /**
     * Deactivated - startDate doesn't matter
     */
    SUSPENDED, //zawieszone
    /**
     * Registrations are FINISHED when endDate is reached
     */
    FINISHED;

}