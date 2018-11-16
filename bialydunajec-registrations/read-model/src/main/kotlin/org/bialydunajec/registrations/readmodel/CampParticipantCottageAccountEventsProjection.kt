package org.bialydunajec.registrations.readmodel

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.registrations.messages.event.CampParticipantCottageAccountExternalEvent
import org.springframework.stereotype.Component

@Component
internal class CampParticipantCottageAccountEventsProjection(
        private val paymentCommitmentRepository: PaymentCommitmentMongoRepository
) : SerializedExternalEventListener() {

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val eventPayload = externalEvent.payload
        when (eventPayload) {
            is CampParticipantCottageAccountExternalEvent.Created -> createProjection(eventPayload)
            is CampParticipantCottageAccountExternalEvent.CommitmentPaid -> {

            }
        }
    }

    private fun createProjection(eventPayload: CampParticipantCottageAccountExternalEvent.Created) {
        fun getPaymentCommitmentReadModelFrom(paymentCommitmentSnapshot: CampParticipantCottageAccountExternalEvent.Created.PaymentCommitmentSnapshot) =
                paymentCommitmentSnapshot.let {
                    PaymentCommitment(
                            it.paymentCommitmentId,
                            PaymentCommitment.Type.CAMP_DOWN_PAYMENT,
                            eventPayload.campRegistrationsEditionId,
                            eventPayload.campParticipant?.let { camper ->
                                PaymentCommitment.CampParticipant(
                                        camper.campParticipantId,
                                        camper.firstName,
                                        camper.lastName,
                                        camper.emailAddress,
                                        camper.phoneNumber
                                )
                            },
                            eventPayload.cottage?.let { cottage ->
                                PaymentCommitment.Cottage(
                                        cottage.cottageId,
                                        cottage.name
                                )
                            },
                            it.amount,
                            it.description,
                            it.deadlineDate,
                            it.paid
                    )
                }

        with(eventPayload) {
            campDownPaymentCommitmentSnapshot
                    ?.let { getPaymentCommitmentReadModelFrom(it) }
                    ?.let { paymentCommitmentRepository.save(it) }

            getPaymentCommitmentReadModelFrom(campParticipationCommitmentSnapshot)
                    .let { paymentCommitmentRepository.save(it) }

            campBusCommitmentSnapshot
                    ?.let { getPaymentCommitmentReadModelFrom(it) }
                    ?.let { paymentCommitmentRepository.save(it) }
        }

    }
}