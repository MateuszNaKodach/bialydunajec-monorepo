package org.bialydunajec.registrations.messages.event


sealed class CampParticipantExternalEvent {

    data class CampParticipantRegistered(
        val campParticipantId: String,
        val campRegistrationsEditionId: String
    ) : CampParticipantExternalEvent(){}
}