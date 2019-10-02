package org.bialydunajec.email.application.external.event.listener

import org.bialydunajec.ddd.application.base.external.event.ExternalEventListener
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.application.api.EmailAddressCommand
import org.bialydunajec.email.application.api.EmailCommandGateway
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import org.bialydunajec.registrations.messages.event.CampParticipantExternalEvent
import org.springframework.stereotype.Component

@Component
internal class RegistrationExternalEventListener(
    private val emailAddressCommandGateway: EmailCommandGateway,
    externalEventSubscriber: ExternalEventSubscriber
) : ExternalEventListener {
    init {
        externalEventSubscriber.subscribe(CampParticipantExternalEvent.CampParticipantRegistered::class) {
            val payload = it.payload
            emailAddressCommandGateway.process(
                    EmailAddressCommand.CatalogizeEmailAddress(
                            EmailAddress(payload.snapshot.currentCamperData.emailAddress),
                            EmailAddressGroup(payload.snapshot.currentCamperData.cottage.cottageName),
                            EmailAddressOwner(
                                    FirstName(payload.snapshot.currentCamperData.personalData.firstName),
                                    LastName(payload.snapshot.currentCamperData.personalData.lastName)
                            )
                    )
            )
        }

        externalEventSubscriber.subscribe(CampParticipantExternalEvent.CampParticipantConfirmed::class) {
            val payload = it.payload
            emailAddressCommandGateway.process(
                    EmailAddressCommand.CatalogizeEmailAddress(
                            EmailAddress(payload.snapshot.currentCamperData.emailAddress),
                            EmailAddressGroup(payload.snapshot.currentCamperData.cottage.cottageName),
                            EmailAddressOwner(
                                    FirstName(payload.snapshot.currentCamperData.personalData.firstName),
                                    LastName(payload.snapshot.currentCamperData.personalData.lastName)
                            )
                    )
            )
        }
    }
}
