package org.bialydunajec.registrations.application.external.event.listener

import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventListener
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SpringExternalEventListener
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.external.event.processor.CampEditionExternalEventProcessor
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class CampEditionExternalEventListener(
        private val campEditionExternalEventProcessor: CampEditionExternalEventProcessor,
        private val campRegistrationsCommandGateway: CampRegistrationsAdminCommandGateway,
        externalEventSubscriber: ExternalEventSubscriber
) : ExternalEventListener {

    init {
        externalEventSubscriber.subscribe(CampEditionExternalEvent.CampEditionCreated::class) {
            val payload = it.payload
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.CreateCampRegistrationsEdition(
                            CampRegistrationsEditionId(payload.campEditionId),
                            payload.startDate,
                            payload.endDate,
                            Money(payload.totalPrice),
                            payload.downPaymentAmount?.let { Money(it) }
                    )
            )
        }

        externalEventSubscriber.subscribe(CampEditionExternalEvent.CampEditionDurationUpdated::class) {
            val payload = it.payload
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.UpdateCampRegistrationsEditionDuration(
                            CampRegistrationsEditionId(payload.campEditionId),
                            payload.startDate,
                            payload.endDate
                    )
            )
        }
    }

}
