package org.bialydunajec.registrations.readmodel.camper

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.ddd.application.base.external.event.SpringSerializedExternalEventListener
import org.bialydunajec.registrations.messages.event.CampParticipantCottageAccountExternalEvent
import org.bialydunajec.registrations.messages.event.CampParticipantExternalEvent
import org.bialydunajec.registrations.readmodel.payment.PaymentCommitment
import org.bialydunajec.registrations.readmodel.payment.PaymentCommitmentMongoRepository
import org.springframework.stereotype.Component
import java.time.Instant

@Component
internal class CampParticipantEventsProjection(
        private val campParticipantMongoRepository: CampParticipantMongoRepository,
        private val campParticipantEventStream: CampParticipantEventStream,
        private val paymentCommitmentMongoRepository: PaymentCommitmentMongoRepository,
        eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(CampParticipantExternalEvent.CampParticipantRegistered::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(CampParticipantExternalEvent.CampParticipantConfirmed::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(CampParticipantExternalEvent.CampParticipantUnregisteredByAuthorized::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(CampParticipantCottageAccountExternalEvent.CommitmentPaid::class) {
            processingQueue.process(it)
        }
    }


    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val eventPayload = externalEvent.payload
        when (eventPayload) {
            is CampParticipantExternalEvent.CampParticipantRegistered -> {
                createProjection(eventPayload, externalEvent.eventOccurredAt)
                campParticipantEventStream.updateStreamWith(externalEvent)
            }
            is CampParticipantExternalEvent.CampParticipantConfirmed -> {
                createProjection(eventPayload, externalEvent.eventOccurredAt)
                campParticipantEventStream.updateStreamWith(externalEvent)
            }
            is CampParticipantExternalEvent.CampParticipantUnregisteredByAuthorized -> {
                createProjection(eventPayload, externalEvent.eventOccurredAt)
                campParticipantEventStream.updateStreamWith(externalEvent)
            }
            is CampParticipantCottageAccountExternalEvent.CommitmentPaid -> {
                createProjection(eventPayload)
                campParticipantEventStream.updateStreamWith(externalEvent)
            }
        }
    }

    private fun createProjection(eventPayload: CampParticipantExternalEvent.CampParticipantRegistered, eventOccurredAt: Instant) {
        with(eventPayload.snapshot) {
            CampParticipant(
                    campParticipantId,
                    campRegistrationsEditionId,
                    eventPayload.campParticipantRegistrationId,
                    confirmedApplication,
                    currentCamperData,
                    stayDuration,
                    participationStatus,
                    eventOccurredAt,
                    null,
                    null,
                    null,
                    null
            )
        }.also {
            campParticipantMongoRepository.save(it)
        }
    }

    private fun createProjection(eventPayload: CampParticipantExternalEvent.CampParticipantConfirmed, eventOccurredAt: Instant) {
        campParticipantMongoRepository.findById(eventPayload.campParticipantId)
                .ifPresent {
                    with(eventPayload.snapshot) {
                        it.currentCamperData = currentCamperData
                        it.confirmedApplication = confirmedApplication
                        it.participationStatus = participationStatus
                        it.stayDuration = stayDuration
                        it.confirmationDate = eventOccurredAt
                    }
                    campParticipantMongoRepository.save(it)
                }
    }

    fun createProjection(eventPayload: CampParticipantCottageAccountExternalEvent.CommitmentPaid) {
        campParticipantMongoRepository.findById(eventPayload.campParticipantId)
                .ifPresent { campParticipant ->
                    paymentCommitmentMongoRepository.findById(eventPayload.paymentCommitmentId)
                            .ifPresent { commitment ->
                                when(commitment.type){
                                    PaymentCommitment.Type.CAMP_BUS_SEAT -> campParticipant.campBusSeatPaidDate = eventPayload.paidDate
                                    PaymentCommitment.Type.CAMP_PARTICIPATION -> campParticipant.campParticipationPaidDate = eventPayload.paidDate
                                    PaymentCommitment.Type.CAMP_DOWN_PAYMENT -> campParticipant.downPaymentPaidDate = eventPayload.paidDate
                                }
                            }
                    campParticipantMongoRepository.save(campParticipant)
                }

    }

    private fun createProjection(eventPayload: CampParticipantExternalEvent.CampParticipantUnregisteredByAuthorized,
                                 eventOccurredAt: Instant) {
        campParticipantMongoRepository.deleteById(eventPayload.campParticipantId)
    }

}
