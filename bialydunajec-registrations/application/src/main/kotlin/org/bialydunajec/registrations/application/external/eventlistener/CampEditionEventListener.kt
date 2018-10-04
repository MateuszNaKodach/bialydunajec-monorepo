package org.bialydunajec.registrations.application.external.eventlistener

import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventListener
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class CampEditionEventListener(private val campRegistrationsCommandGateway: CampRegistrationsCommandGateway) : ExternalEventListener {

    @EventListener
    override fun handleExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is CampEditionExternalEvent.CampEditionCreated -> {
                campRegistrationsCommandGateway.process(
                        CampRegistrationsCommand.CreateCampRegistrationsEdition(
                                CampRegistrationsEditionId(payload.campEditionId),
                                payload.startDate,
                                payload.endDate
                        )
                )
            }
            is CampEditionExternalEvent.CampEditionDurationUpdated -> {
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
}