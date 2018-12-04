package org.bialydunajec.registrations.readmodel.statistics

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.registrations.messages.event.*
import org.bialydunajec.registrations.readmodel.camper.CampParticipant
import org.springframework.stereotype.Component
import java.time.Instant

@Component
internal class CampRegistrationsEditionStatisticsEventsProjection(
        private val eventStream: CampRegistrationsEditionStatisticsEventStream,
        private val campRegistrationsEditionStatisticsRepository: CampRegistrationsEditionStatisticsMongoRepository
) : SerializedExternalEventListener() {

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val eventPayload = externalEvent.payload
        when (eventPayload) {
            is CampRegistrationsEditionExternalEvent.CampRegistrationsCreated -> {
                createProjection(eventPayload)
                eventStream.updateStreamWith(externalEvent)
            }
            is CottageExternalEvent.CottageCreated -> {
                createProjection(eventPayload)
                eventStream.updateStreamWith(externalEvent)
            }
            is CottageExternalEvent.CottageUpdated -> {
                createProjection(eventPayload)
                eventStream.updateStreamWith(externalEvent)
            }
            is CampParticipantExternalEvent.CampParticipantRegistered -> {
                createProjection(eventPayload, externalEvent.eventOccurredAt)
                eventStream.updateStreamWith(externalEvent)
            }
            is ShirtOrderExternalEvent.OrderPlaced -> {
                createProjection(eventPayload)
                eventStream.updateStreamWith(externalEvent)
            }
        }
    }

    private fun createProjection(eventPayload: CampRegistrationsEditionExternalEvent.CampRegistrationsCreated) {
        with(eventPayload) {
            CampRegistrationsEditionStatistics(
                    campRegistrationsEditionId
            )
        }.also {
            campRegistrationsEditionStatisticsRepository.save(it)
        }
    }

    private fun createProjection(eventPayload: CottageExternalEvent.CottageCreated) {
        campRegistrationsEditionStatisticsRepository.findByCampRegistrationsEditionId(eventPayload.snapshot.campRegistrationsEditionId)
                ?.also {
                    it.calculateWith(eventPayload)
                }?.also {
                    campRegistrationsEditionStatisticsRepository.save(it)
                }
    }

    private fun createProjection(eventPayload: CottageExternalEvent.CottageUpdated) {
        campRegistrationsEditionStatisticsRepository.findByCampRegistrationsEditionId(eventPayload.snapshot.campRegistrationsEditionId)
                ?.also {
                    it.calculateWith(eventPayload)
                }?.also {
                    campRegistrationsEditionStatisticsRepository.save(it)
                }

    }

    private fun createProjection(eventPayload: CampParticipantExternalEvent.CampParticipantRegistered, eventOccurredAt: Instant) {
        campRegistrationsEditionStatisticsRepository.findByCampRegistrationsEditionId(eventPayload.snapshot.campRegistrationsEditionId)
                ?.also {
                    it.calculateWith(eventPayload)
                }?.also {
                    campRegistrationsEditionStatisticsRepository.save(it)
                }
    }

    private fun createProjection(eventPayload: ShirtOrderExternalEvent.OrderPlaced) {
        campRegistrationsEditionStatisticsRepository.findByCampRegistrationsEditionId(eventPayload.campRegistrationsEditionId)
                ?.also {
                    it.calculateWith(eventPayload)
                }?.also {
                    campRegistrationsEditionStatisticsRepository.save(it)
                }
    }


}