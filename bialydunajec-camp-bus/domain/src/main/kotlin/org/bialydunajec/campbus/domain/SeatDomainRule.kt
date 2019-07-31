package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.DomainRule

sealed class SeatDomainRule(description: String, violationMessage: String? = null) : DomainRule(description, violationMessage) {

    object SeatForReservationCannotBeAlreadyReserved : SeatDomainRule("Miejsce do zarezerwowania nie może być zarezerwowane dla innego pasażera.")

}
