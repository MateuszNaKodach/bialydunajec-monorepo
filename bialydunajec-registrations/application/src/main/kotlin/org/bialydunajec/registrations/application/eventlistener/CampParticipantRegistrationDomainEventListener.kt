package org.bialydunajec.registrations.application.eventlistener

import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage
import org.bialydunajec.ddd.application.base.email.EmailMessageSenderPort
import org.bialydunajec.registrations.application.configuration.properties.BialyDunajecMainFrontendProperties
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationEvent
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampParticipantRegistrationDomainEventListener(
        private val mainFrontendProperties: BialyDunajecMainFrontendProperties,
        private val emailMessageSender: EmailMessageSenderPort,
        private val campParticipantRepository: CampParticipantRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    //@Async
    //@EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    fun handle(event: CampParticipantRegistrationEvent.Created) {
        fun getRegistrationVerificationUrlFor(campParticipantRegistrationId: CampParticipantRegistrationId, verificationCode: String) =
                mainFrontendProperties.registrationVerificationUrl
                        .replace(":campParticipantRegistrationId", campParticipantRegistrationId.toString())
                        .replace(":verificationCode", verificationCode)

        val camperApplication = event.snapshot.camperApplication
        val emailMessage =
                SimpleEmailMessage(
                        camperApplication.emailAddress,
                        "Obóz w Białym Dunajcu - potwierdzenie rejestracji",
                        """Cześć ${camperApplication.personalData.firstName},
                            aby potwierdzić zapis na Obóz, kliknij w link:
                            ${getRegistrationVerificationUrlFor(event.snapshot.campParticipantRegistrationId, event.snapshot.verificationCode)}"""
                )
        emailMessageSender.sendEmailMessage(emailMessage)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    fun handleCampParticipantRegistrationError(event: CampParticipantRegistrationEvent.Created) {
        val camperApplication = event.snapshot.camperApplication
        val emailMessage =
                SimpleEmailMessage(
                        camperApplication.emailAddress,
                        "Obóz w Białym Dunajcu - rejestracja nie powiodła się",
                        """Cześć ${camperApplication.personalData.firstName}, niestety Twój zapis na obóz się nie powiódł. Ktoś musiał zająć Twoje miejsce..."""
                )
        emailMessageSender.sendEmailMessage(emailMessage)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    fun handle(event: CampParticipantRegistrationEvent.VerifiedByCamper) =
            campParticipantRepository.findById(event.snapshot.campParticipantId)
                    ?.apply { this.confirmByCamperWith(event.snapshot.camperApplication) }
                    ?.apply { campParticipantRepository.save(this) }
                    /*?.also {
                        val emailMessage =
                                SimpleEmailMessage(
                                        it.getEmailAddress(),
                                        "Obóz w Białym Dunajcu - informacje o obozie",
                                        """Cześć ${it.getPersonalData().firstName}, potwierdziłeś swoje uczestnictwo, więc
                                        przesyłamy Ci garść potrzebnych informacji o Obozie:""".trimMargin()
                                )
                        emailMessageSender.sendEmailMessage(emailMessage)
                    }*/


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    fun handle(event: CampParticipantRegistrationEvent.VerifiedByAuthorized) =
            campParticipantRepository.findById(event.snapshot.campParticipantId)
                    ?.apply { this.confirmByAuthorized() }
                    ?.apply { campParticipantRepository.save(this) }
                    /*?.also {
                        val emailMessage =
                                SimpleEmailMessage(
                                        it.getEmailAddress(),
                                        "Obóz w Białym Dunajcu - informacje o obozie",
                                        """Cześć ${it.getPersonalData().firstName}, administrator potwierdził Twoje uczestnictwo, więc
                                        przesyłamy Ci garść potrzebnych informacji o Obozie:""".trimMargin()
                                )
                        emailMessageSender.sendEmailMessage(emailMessage)
                    }*/


}