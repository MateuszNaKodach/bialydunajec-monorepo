package org.bialydunajec.campedition.application

import org.bialydunajec.campedition.application.command.api.CampEditionCommandGateway
import org.bialydunajec.campedition.application.query.api.CampEditionQueryGateway
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandBus
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.base.event.DomainEventBus

internal class CampEditionModule(
        val commandGateway: CampEditionCommandGateway,
        val queryGateway: CampEditionQueryGateway,
        val domainEvents: DomainEventBus,
        val externalCommands: ExternalCommandBus,
        val externalEvents: ExternalEventPublisher
)
