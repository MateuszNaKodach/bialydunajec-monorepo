package org.bialydunajec.campschedule.application.external

import org.axonframework.commandhandling.gateway.CommandGateway
import org.bialydunajec.campedition.messages.event.CampEditionExternalEvent
import org.bialydunajec.campschedule.domain.CampEditionScheduleCommand
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Component
class CampEditionExternalEventProcessor(
        private val commandGateway: CommandGateway
) {

    fun process(eventPayload: CampEditionExternalEvent.CampEditionCreated) {
        commandGateway.send<CampEditionScheduleCommand.StartCampEditionScheduling>(
                CampEditionScheduleCommand.StartCampEditionScheduling(
                        CampEditionScheduleId(eventPayload.campEditionId),
                        eventPayload.startDate,
                        eventPayload.endDate
                )
        )
    }

}
