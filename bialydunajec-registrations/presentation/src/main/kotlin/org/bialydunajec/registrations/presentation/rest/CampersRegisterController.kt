package org.bialydunajec.registrations.presentation.rest

import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.domain.campedition.CampEditionEvent
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrationsId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/event")
class CampersRegisterController(
        val commandGateway: CampRegistrationsCommandGateway
) {

    @GetMapping
    fun postEvent() {
        val campEdition36Id = CampEditionId(36)

        commandGateway.process(CampRegistrationsCommand.StartCampRegistrationsNow(campEdition36Id))
    }

}