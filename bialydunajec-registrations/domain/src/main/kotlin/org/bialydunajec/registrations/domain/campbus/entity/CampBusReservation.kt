package org.bialydunajec.registrations.domain.campbus.entity

/**
 * Na autokar mogą zapisać się tylko uczestnicy Obozu
 */
internal class CampBusReservation(
        val campBusSeatReservationsId: String,
        val campParticipantId: String
)