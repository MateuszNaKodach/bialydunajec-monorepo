package org.bialydunajec.registrations.application.external.event.listener

import org.bialydunajec.academicministry.messages.event.AcademicMinistryExternalEvent
import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventListener
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.external.event.processor.AcademicMinistryExternalEventProcessor
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class AcademicMinistryExternalEventListener(
        private val academicMinistryExternalEventProcessor: AcademicMinistryExternalEventProcessor
) : ExternalEventListener {

    @EventListener
    override fun handleExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is AcademicMinistryExternalEvent.AcademicMinistryCreated ->
                academicMinistryExternalEventProcessor.process(payload)

            is AcademicMinistryExternalEvent.AcademicMinistryUpdated ->
                academicMinistryExternalEventProcessor.process(payload)
        }
    }
}