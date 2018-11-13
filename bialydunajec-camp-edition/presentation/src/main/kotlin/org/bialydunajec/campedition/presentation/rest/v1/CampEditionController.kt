package org.bialydunajec.campedition.presentation.rest.v1

import org.bialydunajec.campedition.application.command.api.CampEditionCommand
import org.bialydunajec.campedition.application.command.api.CampEditionCommandGateway
import org.bialydunajec.campedition.application.query.api.CampEditionQuery
import org.bialydunajec.campedition.application.query.api.CampEditionQueryGateway
import org.bialydunajec.campedition.presentation.rest.v1.request.CreateCampEditionRequest
import org.bialydunajec.campedition.presentation.rest.v1.request.UpdateCampEditionDurationRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/camp-edition")
class CampEditionController(
        private val campEditionCommandGateway: CampEditionCommandGateway,
        private val campEditionQueryGateway: CampEditionQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCampEdition(@RequestBody request: CreateCampEditionRequest) = campEditionCommandGateway.process(
            CampEditionCommand.CreateCampEdition.from(request.campEditionId, request.campEditionStartDate, request.campEditionEndDate, request.campEditionPrice)
    )

    @PatchMapping("/{campEditionId}/duration")
    fun updateCampEditionDurationById(@PathVariable campEditionId: Int, @RequestBody request: UpdateCampEditionDurationRequest) = campEditionCommandGateway.process(
            CampEditionCommand.UpdateCampEditionDuration.from(campEditionId, request.campEditionStartDate, request.campEditionEndDate)
    )

    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping
    fun getAllCampEditions() =
            campEditionQueryGateway.process(CampEditionQuery.All())
                    .sortedByDescending { it.campEditionId.toInt() }


    @GetMapping("/{campEditionId}")
    fun getCampEditionById(@PathVariable campEditionId: Int) =
            campEditionQueryGateway.process(CampEditionQuery.ById(campEditionId))
}