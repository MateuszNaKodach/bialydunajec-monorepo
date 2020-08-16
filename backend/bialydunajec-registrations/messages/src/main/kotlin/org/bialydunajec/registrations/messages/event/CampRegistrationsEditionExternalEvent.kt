package org.bialydunajec.registrations.messages.event


sealed class CampRegistrationsEditionExternalEvent {

    class CampRegistrationsCreated(
            val campRegistrationsEditionId: String,
            val campRegistrationsId: String
    ) : CampRegistrationsEditionExternalEvent()

}