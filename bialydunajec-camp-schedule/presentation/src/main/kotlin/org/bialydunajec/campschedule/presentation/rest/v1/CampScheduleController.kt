package org.bialydunajec.campschedule.presentation.rest.v1

import org.axonframework.commandhandling.gateway.CommandGateway
import org.bialydunajec.campschedule.domain.CampEditionScheduleCommand
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/rest-api/v1/camp-schedule")
internal class CampScheduleController(val commandGateway: CommandGateway) {

/*
    @PostMapping
    fun startCampScheduling(): CompletableFuture<CampEditionScheduleId> =
            commandGateway.send<CampEditionScheduleId>(
                    CampEditionScheduleCommand.StartCampEditionScheduling(CampEditionScheduleId(36))
            )
*/

}
