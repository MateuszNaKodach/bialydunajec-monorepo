package org.bialydunajec.registrations.application.dto

data class CampParticipantCountByCottageIdDto(
        val cottageId: String,
        val campParticipantsCount: Long
)