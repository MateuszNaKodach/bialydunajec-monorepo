package org.bialydunajec.registrations.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.registrations.application.dto.toDto
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantReadOnlyRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.shirt.ShirtOrderEvent
import org.bialydunajec.registrations.messages.event.ShirtOrderExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class ShirtOrderDomainEventsPropagator(
        private val campParticipantReadOnlyRepository: CampParticipantReadOnlyRepository,
        private val cottageRepository: CottageRepository,
        private val externalEventBus: ExternalEventPublisher
) {

    @Async
    @TransactionalEventListener
    fun handle(event: ShirtOrderEvent.OrderPlaced) {
        with(event) {
            campParticipantReadOnlyRepository.findById(snapshot.campParticipantId)
                    ?.let { participant ->
                        val cottage = cottageRepository.findById(participant.getCottageId())!!

                        externalEventBus.send(
                                ShirtOrderExternalEvent.OrderPlaced(
                                        participant.getCampRegistrationsEditionId().toString(),
                                        aggregateId.toString(),
                                        ShirtOrderExternalEvent.OrderPlaced.CampParticipant(
                                                participant.getAggregateId().toString(),
                                                participant.getPersonalData().pesel.toStringOrNull(),
                                                participant.getPersonalData().firstName.toStringOrNull(),
                                                participant.getPersonalData().lastName.toStringOrNull(),
                                                participant.getEmailAddress().toStringOrNull(),
                                                participant.getPhoneNumber().toStringOrNull(),
                                                ShirtOrderExternalEvent.OrderPlaced.Cottage(cottage.getAggregateId().toString(), cottage.getName()),
                                                participant.participationStatus.toDto()
                                        ),
                                        ShirtOrderExternalEvent.OrderPlaced.ShirtOrder(
                                                snapshot.campEditionShirtId.toString(),
                                                snapshot.colorOption.shirtColorOptionId.toString(),
                                                snapshot.colorOption.color.name,
                                                snapshot.sizeOption.shirtSizeOptionId.toString(),
                                                snapshot.sizeOption.size.name,
                                                snapshot.sizeOption.size.type.toDto()
                                        ),
                                        event.occurredAt
                                )
                        )
                    }

        }

    }
}
