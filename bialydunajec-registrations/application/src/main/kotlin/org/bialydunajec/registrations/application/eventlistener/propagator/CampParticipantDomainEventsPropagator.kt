package org.bialydunajec.registrations.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.command.ExternalCommandBus
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.email.messages.command.EmailExternalCommand
import org.bialydunajec.registrations.application.dto.from
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantEvent
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.dto.CampParticipantDto
import org.bialydunajec.registrations.messages.event.CampParticipantExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class CampParticipantDomainEventsPropagator(
        private val externalEventBus: ExternalEventPublisher,
        private val externalCommandBus: ExternalCommandBus,
        private val cottageRepository: CottageRepository,
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository
) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: CampParticipantEvent.Registered) {
        with(domainEvent) {
            externalEventBus.send(
                    CampParticipantExternalEvent.CampParticipantRegistered(
                            aggregateId.toString(),
                            campParticipantRegistrationRepository.findByCampParticipantId(aggregateId)?.getAggregateId()?.toStringOrNull(),
                            with(domainEvent.snapshot){
                                CampParticipantDto.from(
                                        this,
                                        confirmedApplication?.let { cottageRepository.findById(it.cottageId)?.getSnapshot() },
                                        currentCamperData.let { cottageRepository.findById(it.cottageId)!!.getSnapshot() }
                                )
                            }
                    )
            )

            externalCommandBus.send(
                EmailExternalCommand.CatalogizeEmail(
                    snapshot.currentCamperData.emailAddress + "",
                    snapshot.currentCamperData.emailAddress,
                    snapshot.currentCamperData.personalData.firstName.toString(),
                    snapshot.currentCamperData.personalData.lastName.toString(),
                    "EMAIL_GROUP_NAME"
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
                            with(domainEvent.snapshot){
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
    fun handleDomainEvent(domainEvent: CampParticipantEvent.UnregisteredByAuthorized) {
        with(domainEvent) {
            externalEventBus.send(
                    CampParticipantExternalEvent.CampParticipantUnregisteredByAuthorized(
                            aggregateId.toString(),
                            with(domainEvent.snapshot){
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
