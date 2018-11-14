package org.bialydunajec.registrations.application.eventlistener

import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage
import org.bialydunajec.ddd.application.base.email.EmailMessageSenderPort
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantEvent
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPaymentRepository
import org.bialydunajec.registrations.domain.payment.PaymentCommitment
import org.bialydunajec.registrations.domain.payment.PaymentCommitmentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampParticipantDomainEventListener(
        private val campParticipationPaymentRepository: CampParticipationPaymentRepository,
        private val paymentCommitmentRepository: PaymentCommitmentRepository,
        private val emailMessageSender: EmailMessageSenderPort
) {


    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handle(event: CampParticipantEvent.Confirmed) {
        val participationPayment = campParticipationPaymentRepository.findByCampParticipantId(event.aggregateId)!!
        val paymentCommitment = PaymentCommitment.createFrom(participationPayment)
                .also { paymentCommitmentRepository.save(it) }
        participationPayment.also {
            it.paymentCommitmentId = paymentCommitment.getAggregateId()
            campParticipationPaymentRepository.save(it)
        }

        with(event.snapshot.currentCamperData) {
            val emailMessage =
                    SimpleEmailMessage(
                            event.snapshot.currentCamperData.emailAddress,
                            "Obóz w Białym Dunajcu - naliczenie opłaty",
                            """Cześć ${personalData.firstName},
                            została naliczona opłata za wyjazd na Obóz w wysokości: ${paymentCommitment.amount} złotych"""
                    )
            emailMessageSender.sendEmailMessage(emailMessage)
        }
    }
}