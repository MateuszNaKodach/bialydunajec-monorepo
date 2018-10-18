package org.bialydunajec.registrations.application.eventlistener

import org.bialydunajec.ddd.application.base.email.EmailMessage
import org.bialydunajec.ddd.application.base.email.EmailMessageSender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.registrations.application.configuration.properties.BialyDunajecMainFrontendProperties
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationEvent
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampParticipantRegistrationDomainEventListener(
        private val mainFrontendProperties: BialyDunajecMainFrontendProperties,
        private val emailMessageSender: EmailMessageSender,
        private val campParticipantRepository: CampParticipantRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Async
    @TransactionalEventListener
    fun handle(event: CampParticipantRegistrationEvent.Created) {
        fun getRegistrationVerificationUrlFor(campParticipantRegistrationId: CampParticipantRegistrationId, verificationCode: String) =
                mainFrontendProperties.registrationVerificationUrl
                        .replace(":campParticipantRegistrationId", campParticipantRegistrationId.toString())
                        .replace(":verificationCode", verificationCode)

        val camperApplication = event.snapshot.camperApplication
        val emailMessage =
                EmailMessage(
                        camperApplication.emailAddress,
                        "Obóz w Białym Dunajcu - potwierdzenie rejestracji",
                        """Cześć ${camperApplication.personalData.firstName},
                            aby potwierdzić zapis na Obóz, kliknij w link:
                            ${getRegistrationVerificationUrlFor(event.snapshot.campParticipantRegistrationId, event.snapshot.verificationCode)}"""
                )
        emailMessageSender.sendEmailMessage(emailMessage)

        log.info("Email message sent {}", emailMessage)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    fun handle(event: CampParticipantRegistrationEvent.VerifiedByCamper) {
        campParticipantRepository.findById(event.snapshot.campParticipantId)
                .apply { this?.confirmByCamperWith(event.snapshot.camperApplication) }
    }


}