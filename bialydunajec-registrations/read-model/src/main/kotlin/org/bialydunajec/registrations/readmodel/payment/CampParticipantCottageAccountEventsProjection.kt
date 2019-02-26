package org.bialydunajec.registrations.readmodel.payment

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.ddd.application.base.external.event.SpringSerializedExternalEventListener
import org.bialydunajec.registrations.messages.event.CampParticipantCottageAccountExternalEvent
import org.springframework.stereotype.Component

//TODO: Listen to camp participant, cottage and payment commitment updates!
//TODO: Zapisywanie eventów dałoby możliwośc przeprocesowania ich wszystkich jeszcze raz i odtworzenia widoków.
@Component
internal class CampParticipantCottageAccountEventsProjection(
        private val paymentCommitmentRepository: PaymentCommitmentMongoRepository,
        private val eventStream: CampParticipantCottageAccountEventStream,
        eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(CampParticipantCottageAccountExternalEvent.Created::class.java) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(CampParticipantCottageAccountExternalEvent.CommitmentPaid::class.java) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val eventPayload = externalEvent.payload
        when (eventPayload) {
            is CampParticipantCottageAccountExternalEvent.Created -> {
                createProjection(eventPayload)
                eventStream.updateStreamWith(externalEvent)
            }
            is CampParticipantCottageAccountExternalEvent.CommitmentPaid -> {
                createProjection(eventPayload)
                eventStream.updateStreamWith(externalEvent)
            }
        }
    }

    //TODO: Add possibility to update existing projection from only paid!
    private fun createProjection(eventPayload: CampParticipantCottageAccountExternalEvent.Created) {
        fun getPaymentCommitmentReadModelFrom(
                paymentCommitmentSnapshot: CampParticipantCottageAccountExternalEvent.Created.PaymentCommitmentSnapshot,
                paymentType: PaymentCommitment.Type) =
                paymentCommitmentSnapshot.let {
                    PaymentCommitment(
                            it.paymentCommitmentId,
                            paymentType,
                            eventPayload.campRegistrationsEditionId,
                            eventPayload.campParticipant?.let { camper ->
                                PaymentCommitment.CampParticipant(
                                        camper.campParticipantId,
                                        camper.pesel,
                                        camper.firstName,
                                        camper.lastName,
                                        camper.emailAddress,
                                        camper.phoneNumber
                                )
                            },
                            eventPayload.campParticipantCottageAccountId,
                            eventPayload.cottage?.let { cottage ->
                                PaymentCommitment.Cottage(
                                        cottage.cottageId,
                                        cottage.name
                                )
                            },
                            it.amount,
                            it.description,
                            it.deadlineDate,
                            it.paid,
                            null
                    )
                }

        with(eventPayload) {
            campDownPaymentCommitmentSnapshot
                    ?.let { getPaymentCommitmentReadModelFrom(it, PaymentCommitment.Type.CAMP_DOWN_PAYMENT) }
                    ?.let { paymentCommitmentRepository.save(it) }

            getPaymentCommitmentReadModelFrom(campParticipationCommitmentSnapshot, PaymentCommitment.Type.CAMP_PARTICIPATION)
                    .let { paymentCommitmentRepository.save(it) }

            campBusCommitmentSnapshot
                    ?.let { getPaymentCommitmentReadModelFrom(it, PaymentCommitment.Type.CAMP_BUS_SEAT) }
                    ?.let { paymentCommitmentRepository.save(it) }
        }
    }

    fun createProjection(eventPayload: CampParticipantCottageAccountExternalEvent.CommitmentPaid) {
        paymentCommitmentRepository.findById(eventPayload.paymentCommitmentId)
                .ifPresent {
                    it.isPaid = true
                    it.paidDate = eventPayload.paidDate
                    paymentCommitmentRepository.save(it)
                }

        if (eventPayload.commitmentType == PaymentCommitment.Type.CAMP_DOWN_PAYMENT.name) {
            val campParticipantPaymentCommitments = paymentCommitmentRepository.findAllByCampParticipantCampParticipantId(eventPayload.campParticipantId)
            campParticipantPaymentCommitments.forEach {
                it.campDownPaymentIsPaid = true
            }
            paymentCommitmentRepository.saveAll(campParticipantPaymentCommitments)
        }
    }


}
