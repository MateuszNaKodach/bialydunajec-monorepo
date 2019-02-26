package org.bialydunajec.registrations.readmodel.shirt

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.ddd.application.base.external.event.SpringSerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailMessageLogExternalEvent
import org.bialydunajec.registrations.messages.event.CampParticipantExternalEvent
import org.bialydunajec.registrations.messages.event.ShirtOrderExternalEvent
import org.springframework.stereotype.Component

@Component
internal class ShirtOrderEventsProjection(
        private val shirtOrderMongoRepository: ShirtOrderMongoRepository,
        private val shirtOrderEventStream: ShirtOrderEventStream,
        eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(ShirtOrderExternalEvent.OrderPlaced::class.java) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(CampParticipantExternalEvent.CampParticipantConfirmed::class.java) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val eventPayload = externalEvent.payload
        when (eventPayload) {
            is ShirtOrderExternalEvent.OrderPlaced -> {
                createProjection(eventPayload)
                shirtOrderEventStream.updateStreamWith(externalEvent)
            }
            is CampParticipantExternalEvent.CampParticipantConfirmed -> {
                createProjection(eventPayload)
                shirtOrderEventStream.updateStreamWith(externalEvent)
            }
        }
    }

    private fun createProjection(eventPayload: ShirtOrderExternalEvent.OrderPlaced) {
        with(eventPayload) {
            ShirtOrder(
                    shirtOrderId,
                    campRegistrationsEditionId,
                    ShirtOrder.CampParticipant(
                            campParticipant.campParticipantId,
                            ShirtOrder.Cottage(
                                    campParticipant.cottage.cottageId,
                                    campParticipant.cottage.name
                            ),
                            campParticipant.pesel,
                            campParticipant.firstName,
                            campParticipant.lastName,
                            campParticipant.emailAddress,
                            campParticipant.phoneNumber,
                            campParticipant.participationStatus
                    ),
                    shirtOrder.campEditionShirtId,
                    ShirtOrder.SelectedOptions(
                            shirtOrder.shirtColorOptionId,
                            shirtOrder.colorName,
                            shirtOrder.shirtSizeOptionId,
                            shirtOrder.sizeName,
                            shirtOrder.shirtType
                    ),
                    placedDate
            )
        }.also {
            shirtOrderMongoRepository.save(it)
        }
    }

    private fun createProjection(eventPayload: CampParticipantExternalEvent.CampParticipantConfirmed) {
        shirtOrderMongoRepository.findAllByCampParticipantCampParticipantId(eventPayload.campParticipantId)
                .forEach {
                    with(eventPayload.snapshot) {
                        it.campParticipant.participationStatus = participationStatus
                    }
                    shirtOrderMongoRepository.save(it)
                }
    }

}
