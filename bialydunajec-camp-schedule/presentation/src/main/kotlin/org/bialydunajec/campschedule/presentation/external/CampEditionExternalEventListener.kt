package org.bialydunajec.campschedule.presentation.external

import org.axonframework.commandhandling.gateway.CommandGateway
import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.springframework.stereotype.Component

@Component
internal class CampEditionExternalEventListener internal constructor(
        val commandGateway: CommandGateway
): SerializedExternalEventListener() {

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
