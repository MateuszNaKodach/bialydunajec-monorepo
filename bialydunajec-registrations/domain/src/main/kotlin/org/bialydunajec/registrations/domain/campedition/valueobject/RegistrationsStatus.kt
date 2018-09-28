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
     * Registrations are activated and will be InProgress when reach startDate
     */
    //ACTIVATED, //TODO: Delete this option, and configured will be IN_PROGRESS when reached time or not if time is not set!


        /**
     * Registrations startDate reached
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