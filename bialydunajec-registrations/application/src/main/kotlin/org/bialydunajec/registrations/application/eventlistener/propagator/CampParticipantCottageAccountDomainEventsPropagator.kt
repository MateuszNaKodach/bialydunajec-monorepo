package org.bialydunajec.registrations.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantReadOnlyRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountEvent
import org.bialydunajec.registrations.domain.payment.valueobject.PaymentCommitmentSnapshot
import org.bialydunajec.registrations.messages.event.CampParticipantCottageAccountExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampParticipantCottageAccountDomainEventsPropagator(
        private val externalEventBus: ExternalEventPublisher,
        private val campParticipantRepository: CampParticipantReadOnlyRepository,
        private val cottageRepository: CottageRepository
) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampParticipantCottageAccountEvent.Created) {
        fun externalPaymentCommitment(paymentCommitmentSnapshot: PaymentCommitmentSnapshot) =
                with(paymentCommitmentSnapshot) {
                    CampParticipantCottageAccountExternalEvent.Created.PaymentCommitmentSnapshot(
                            paymentCommitmentId.toString(),
                            initialAmount.getValue().toDouble(),
                            description,
                            deadlineDate,
                            amount.getValue().toDouble(),
                            paid
                    )
                }

        val campParticipant = campParticipantRepository.findById(domainEvent.campParticipantId)
        val cottage = cottageRepository.findById(domainEvent.cottageId)

        with(domainEvent) {
            externalEventBus.send(
                    CampParticipantCottageAccountExternalEvent.Created(
                            aggregateId.toString(),
                            campParticipant?.let {
                                CampParticipantCottageAccountExternalEvent.Created.CampParticipant(
                                        it.getAggregateId().toString(),
                                        it.getPersonalData().pesel.toStringOrNull(),
                                        it.getPersonalData().firstName.toStringOrNull(),
                                        it.getPersonalData().lastName.toStringOrNull(),
                                        it.getEmailAddress().toStringOrNull(),
                                        it.getPhoneNumber().toStringOrNull()
                                )
                            },
                            cottage?.let {
                                CampParticipantCottageAccountExternalEvent.Created.Cottage(
                                        it.getAggregateId().toString(),
                                        it.getName()
                                )
                            },
                            cottage?.getCampEditionId().toStringOrNull(),
                            campDownPaymentCommitmentSnapshot?.let { externalPaymentCommitment(it) },
                            externalPaymentCommitment(campParticipationCommitmentSnapshot),
                            campBusCommitmentSnapshot?.let { externalPaymentCommitment(it) }
                    )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampParticipantCottageAccountEvent.CommitmentPaid) {
        with(domainEvent) {
            externalEventBus.send(
                    CampParticipantCottageAccountExternalEvent.CommitmentPaid(
                            aggregateId.toString(),
                            paymentCommitmentId.toString(),
                            paidDate,
                            campParticipantId.toString(),
                            commitmentType.name
                    )
            )
        }
    }
}
