package org.bialydunajec.registrations.domain.camper.valueobject

/*
Original application sent by camper - entity - @OneToOne with CampParticipant
 */

enum class ParticipationStatus {
    WAITING_FOR_CONFIRM,
    CONFIRMED_BY_CAMPER,
    CANCELLED_BY_CAMPER,
    CONFIRMED_BY_AUTHORIZED,
    CANCELLED_BY_AUTHORIZED,
    CANCELLED_BY_DEADLINE;
}