package org.bialydunajec.registrations.application.eventlistener

import org.bialydunajec.ddd.application.base.email.EmailMessageSenderPort
import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantEvent
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountRepository
import org.bialydunajec.registrations.domain.shirt.ShirtOrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampParticipantDomainEventListener(
        private val campParticipantCottageAccountRepository: CampParticipantCottageAccountRepository,
        private val cottageRepository: CottageRepository,
        private val emailMessageSender: EmailMessageSenderPort,
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository,
        private val shirtOrderRepository: ShirtOrderRepository
) {


    //TODO: Better handling when cottage or camp participant account not found!
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handle(event: CampParticipantEvent.Confirmed) {
        val campParticipantAccount = campParticipantCottageAccountRepository
                .findByCampParticipantIdAndCottageId(event.snapshot.campParticipantId, event.snapshot.currentCamperData.cottageId)!!

        val cottage = cottageRepository.findById(event.snapshot.currentCamperData.cottageId)!!

        val campParticipantPersonalData = event.snapshot.currentCamperData.personalData


        //TODO: Tworzenie całego maila o zapisaniu się!
        val downPaymentCommitmentSnapshot = campParticipantAccount.getCampDownPaymentCommitmentSnapshot()
        if (downPaymentCommitmentSnapshot != null) {
            with(campParticipantAccount) {
                val emailMessage =
                        SimpleEmailMessage(
                                event.snapshot.currentCamperData.emailAddress,
                                "Obóz w Białym Dunajcu - ważne informacje",
                                """
                                Cześć ${campParticipantPersonalData.firstName},
                                bardzo cieszymy się, że ${if (campParticipantPersonalData.gender == Gender.MALE) "potwierdziłeś" else "potwierdziłaś"} Twój zapis na Obóz!
                                Prosimy o bardzo dokładne zapoznanie się z treścią tej wiadomości.
                                Znajdziesz tutaj wszystkie informacje potrzebne, aby bez problemu pojechać na Obóz.
                                Na końcu maila znajduje się także instrukcja płatności.


                                //TODO: INFORMACJE NA TEMAT OBOZU - WERSJA TESTOWA APLIKACJI


                                Prosimy Ciebie jeszcze, żebyś w ciągu tygodnia od dostania tej wiadomości ${if (campParticipantPersonalData.gender == Gender.MALE) "wpłacił" else "wpłaciła"} zadatek w wysokości
                                została naliczona opłata za wyjazd na Obóz w wysokości: ${downPaymentCommitmentSnapshot.amount.getValue()} złotych.
                                Wpłaty należy dokonać na konto Twojej Chatki: ${cottage.getBankTransferDetails()?.accountNumber}
                                Najlepiej jak w tytule przelewu wpiszesz: "${campParticipantPersonalData.firstName} ${campParticipantPersonalData.lastName} - ${event.snapshot.campRegistrationsEditionId} Biały Dunajec"
                                Jeśli tego nie zrobisz, niestety będziemy musieli zwolnić Twoje miejsce komuś innemu :(

                                Zadatek jest częścią opłaty za cały Obóz, więc zostanie Ci już do zapłacenia tylko ${campParticipantAccount.getCampParticipationCommitmentSnapshot().amount.getValue()} złotych w trakcie Obozu.


                                Do zobaczenia już niedługo!

                                Pozdrawiamy,
                                organizatorzy Obozu Duszpasterstw Akademickich Wrocławia i Opola w Białym Dunajcu


                                Jeśli masz jakieś pytania śmiało pisz do szefa swojej chatki${if (cottage.getCottageBoss()?.emailAddress != null) " na adres: ${cottage.getCottageBoss()?.emailAddress}" else "!"}
                            """
                        )
                emailMessageSender.sendEmailMessage(emailMessage)
            }
        }

    }


    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleUnregisteredToDeleteShirtOrder(event: CampParticipantEvent.UnregisteredByAuthorized) {

        val shirtOrder = shirtOrderRepository.findByCampParticipantId(event.aggregateId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_ORDER_TO_DELETE_MUST_EXISTS)

        shirtOrder.cancel()
        shirtOrderRepository.delete(shirtOrder)
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleUnregisteredToPaymentsCommitments(event: CampParticipantEvent.UnregisteredByAuthorized) {

        val campParticipantAccount = campParticipantCottageAccountRepository
                .findByCampParticipantIdAndCottageId(event.snapshot.campParticipantId, event.snapshot.currentCamperData.cottageId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_COTTAGE_ACCOUNT_TO_CLOSE_MUST_EXISTS)

        campParticipantAccount.close()
        campParticipantCottageAccountRepository.delete(campParticipantAccount)
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleUnregisteredToUpdateRegistrationStatus(event: CampParticipantEvent.UnregisteredByAuthorized) {

        val campParticipantRegistration = campParticipantRegistrationRepository.findByCampParticipantId(event.aggregateId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_REGISTRATIONS_TO_CANCEL_MUST_EXISTS)

        campParticipantRegistration.cancelByAuthorized()
        campParticipantRegistrationRepository.save(campParticipantRegistration)
    }


}
