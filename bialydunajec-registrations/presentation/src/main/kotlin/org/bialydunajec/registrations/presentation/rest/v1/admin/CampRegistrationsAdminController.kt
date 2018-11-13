package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.query.api.CampEditionQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsEditionQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsQueryGateway
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.UpdateCampRegistrationsTimerRequest
import org.springframework.web.bind.annotation.*

//TODO: Change to /rest-api/admin/v1
@RestController
@RequestMapping("/rest-api/v1/admin/camp-registrations")
class CampRegistrationsAdminController(
        private val commandGateway: CampRegistrationsAdminCommandGateway,
        private val queryGateway: CampRegistrationsQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PatchMapping("/{campRegistrationsEditionId}/timer")
    fun updateCampRegistrationsTimerById(@PathVariable campRegistrationsEditionId: Int, @RequestBody request: UpdateCampRegistrationsTimerRequest) =
            commandGateway.process(
                    CampRegistrationsCommand.UpdateCampRegistrationsTimer(
                            campRegistrationsEditionId,
                            request.timerStartDate,
                            request.timerEndDate
                    )
            )

    @PatchMapping("/{campRegistrationsEditionId}/start")
    fun startCampRegistrationsEditionById(@PathVariable campRegistrationsEditionId: Int) =
            commandGateway.process(CampRegistrationsCommand.StartCampRegistrationsNow(campRegistrationsEditionId))


    @PatchMapping("/{campRegistrationsEditionId}/suspend")
    fun suspendCampRegistrationsEditionById(@PathVariable campRegistrationsEditionId: Int) =
            commandGateway.process(CampRegistrationsCommand.SuspendCampRegistrationsNow(campRegistrationsEditionId))


    @PatchMapping("/{campRegistrationsEditionId}/unsuspend")
    fun unsuspendCampRegistrationsEditionById(@PathVariable campRegistrationsEditionId: Int) =
            commandGateway.process(CampRegistrationsCommand.UnsuspendCampRegistrationsNow(campRegistrationsEditionId))


    @PatchMapping("/{campRegistrationsEditionId}/finish")
    fun finishCampRegistrationsEditionById(@PathVariable campRegistrationsEditionId: Int) =
            commandGateway.process(CampRegistrationsCommand.FinishCampRegistrationsNow(campRegistrationsEditionId))


    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping
    fun getAllCampRegistrations() = queryGateway.process(CampRegistrationsEditionQuery.All())

    @GetMapping("/{campRegistrationsEditionId}")
    fun getCampRegistrationsEditionById(@PathVariable campRegistrationsEditionId: Int) =
            queryGateway.process(CampRegistrationsEditionQuery.ById(campRegistrationsEditionId))

    @GetMapping("/camp-edition")
    fun getAllCampEditions() = queryGateway.process(CampEditionQuery.All())
            .sortedByDescending { it.campEditionId }

    @GetMapping("/{campRegistrationsEditionId}/camp-edition")
    fun getCampEditionByCampRegistrationsEditionId(@PathVariable campRegistrationsEditionId: Int) =
            queryGateway.process(CampEditionQuery.ById(campRegistrationsEditionId))
}