package org.bialydunajec.registrations.messages.event

import org.bialydunajec.registrations.dto.CottageDto


sealed class CottageExternalEvent {

    data class CottageCreated(
            val cottageId: String,
            val snapshot: CottageDto
    ) : CottageExternalEvent()

    data class CottageUpdated(
            val cottageId: String,
            val snapshot: CottageDto
    ) : CottageExternalEvent()
}