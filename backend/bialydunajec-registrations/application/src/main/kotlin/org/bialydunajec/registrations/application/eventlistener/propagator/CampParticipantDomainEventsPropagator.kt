package org.bialydunajec.registrations.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.sharedkernel.extensions.toStringOrNull
import org.bialydunajec.registrations.application.dto.from
import org.bialydunajec.registrations.application.dto.toDtoWithCottage
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantEvent
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.dto.CampParticipantDto
import org.bialydunajec.registrations.messages.event.CampParticipantExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Transactional
@Component
internal class CampParticipantDomainEventsPropagator(
    private val externalEventBus: ExternalEventPublisher,
    private val cottageRepository: CottageRepository,
    private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository,
    private val campParticipantRepository: CampParticipantRepository
) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampParticipantEvent.Registered) {
        with(domainEvent) {
            externalEventBus.send(
                CampParticipantExternalEvent.CampParticipantRegistered(
                    aggregateId.toString(),
                    campParticipantRegistrationRepository.findByCampParticipantId(aggregateId)?.getAggregateId()?.toStringOrNull(),
                    with(domainEvent.snapshot) {
                        CampParticipantDto.from(
                            this,
                            confirmedApplication?.let { cottageRepository.findById(it.cottageId)?.getSnapshot() },
                            currentCamperData.let { cottageRepository.findById(it.cottageId)!!.getSnapshot() }
                        )
                    }
                )
            )

        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampParticipantEvent.Confirmed) {
        with(domainEvent) {
            externalEventBus.send(
                CampParticipantExternalEvent.CampParticipantConfirmed(
                    aggregateId.toString(),
                    with(domainEvent.snapshot) {
                        CampParticipantDto.from(
                            this,
                            confirmedApplication?.let { cottageRepository.findById(it.cottageId)?.getSnapshot() },
                            currentCamperData.let { cottageRepository.findById(it.cottageId)!!.getSnapshot() }
                        )
                    }
                )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampParticipantEvent.CampParticipantDataCorrected) {
        val cottage = campParticipantRepository.findById(domainEvent.aggregateId)!!.let {
            cottageRepository.findById(it.getCottageId())!!
        }
        with(domainEvent) {
            externalEventBus.send(
                CampParticipantExternalEvent.CampParticipantDataCorrected(
                    aggregateId.toString(),
                    cottage.getCampEditionId().getIdentifierValue(),
                    cottage.getAggregateId().getIdentifierValue(),
                    domainEvent.oldCamperData.toDtoWithCottage(cottage.getSnapshot()),
                    domainEvent.newCamperData.toDtoWithCottage(cottage.getSnapshot())
                )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampParticipantEvent.UnregisteredByAuthorized) {
        with(domainEvent) {
            externalEventBus.send(
                CampParticipantExternalEvent.CampParticipantUnregisteredByAuthorized(
                    aggregateId.toString(),
                    with(domainEvent.snapshot) {
                        CampParticipantDto.from(
                            this,
                            confirmedApplication?.let { cottageRepository.findById(it.cottageId)?.getSnapshot() },
                            currentCamperData.let { cottageRepository.findById(it.cottageId)!!.getSnapshot() }
                        )
                    }
                )
            )
        }
    }


}
