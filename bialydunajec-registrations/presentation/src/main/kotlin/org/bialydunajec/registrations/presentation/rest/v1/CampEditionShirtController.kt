package org.bialydunajec.registrations.presentation.rest.v1

import org.bialydunajec.registrations.application.query.api.CampEditionShirtQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsQueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api/v1/camp-registrations/camp-shirt")
internal class CampEditionShirtController(
        private val queryGateway: CampRegistrationsQueryGateway
) {


    @GetMapping
    fun getCampEditionShirtByCampRegistrationsEditionId(@RequestParam campRegistrationsEditionId: String) =
        queryGateway.process(CampEditionShirtQuery.ByCampRegistrationsEditionId(campRegistrationsEditionId))


}