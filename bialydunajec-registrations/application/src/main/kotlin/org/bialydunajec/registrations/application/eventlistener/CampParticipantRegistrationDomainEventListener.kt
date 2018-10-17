package org.bialydunajec.registrations.application.eventlistener

import org.bialydunajec.ddd.application.base.email.EmailMessage
import org.bialydunajec.ddd.application.base.email.EmailMessageSender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.registrations.application.configuration.properties.BialyDunajecMainFrontendProperties
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationEvent
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampParticipantRegistrationDomainEventListener(
        private val mainFrontendProperties: BialyDunajecMainFrontendProperties,
        private val emailMessageSender: EmailMessageSender
) {

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handle(event: CampParticipantRegistrationEvent.Created) {
        val camperApplication = event.snapshot.camperApplication
        emailMessageSender.sendEmailMessage(
                EmailMessage(
                        camperApplication.emailAddress,
                        "Obóz w Białym Dunajcu - potwierdzenie rejestracji",
                        """Cześć ${camperApplication.personalData.firstName},
                            aby potwierdzić zapis na Obóz, kliknij w link:
                            ${getRegistrationVerificationUrlFor(event.snapshot.campParticipantRegistrationId, event.snapshot.verificationCode)}"""
                )
        )
    }

    private fun getRegistrationVerificationUrlFor(campParticipantRegistrationId: CampParticipantRegistrationId, verificationCode: String) =
            mainFrontendProperties.registrationVerificationUrl
                    .replace(":campParticipantRegistrationId", campParticipantRegistrationId.toString())
                    .replace(":verificationCode", verificationCode)

}