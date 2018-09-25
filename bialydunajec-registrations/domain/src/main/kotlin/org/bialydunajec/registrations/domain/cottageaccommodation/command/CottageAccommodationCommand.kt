package org.bialydunajec.registrations.domain.cottageaccommodation.command

sealed class CottageAccommodationCommand {
    data class CamperApplicationCommand(val pesel: String) : CottageAccommodationCommand()
}