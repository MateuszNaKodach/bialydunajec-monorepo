package org.bialydunajec.registrations.dto

data class CampParticipantCountByCottageIdDto(
        val cottageId: String,
        val campParticipantsCount: Long
)