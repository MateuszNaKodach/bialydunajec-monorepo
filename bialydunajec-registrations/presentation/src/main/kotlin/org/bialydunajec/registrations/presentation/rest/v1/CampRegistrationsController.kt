package org.bialydunajec.registrations.presentation.rest.v1

import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/rest-api/v1/event")
class CampRegistrationsController(
        private val commandGateway: CampRegistrationsCommandGateway
) {

    val campEdition36Id = CampRegistrationsEditionId(36)

    @GetMapping("/create/{id}")
    fun postEventCreate(@PathVariable id: Int) {
        commandGateway.process(
                CampRegistrationsCommand.CreateCampRegistrationsEdition(
                        CampRegistrationsEditionId(id),
                        LocalDate.of(2021, 8, 1),
                        LocalDate.of(2021, 8, 13)
                )
        )
    }

    @GetMapping("/start")
    fun postEventStart() {
        commandGateway.process(CampRegistrationsCommand.StartCampRegistrationsNow(campEdition36Id))
    }

    @GetMapping("/suspend")
    fun postEventSuspend() {
        commandGateway.process(CampRegistrationsCommand.SuspendCampRegistrationsNow(campEdition36Id))
    }

    @GetMapping("/unsuspend")
    fun postEventUnsuspend() {
        commandGateway.process(CampRegistrationsCommand.UnsuspendCampRegistrationsNow(campEdition36Id))
    }

    @GetMapping("/finish")
    fun postEventFinish() {
        commandGateway.process(CampRegistrationsCommand.FinishCampRegistrationsNow(campEdition36Id))
    }

}