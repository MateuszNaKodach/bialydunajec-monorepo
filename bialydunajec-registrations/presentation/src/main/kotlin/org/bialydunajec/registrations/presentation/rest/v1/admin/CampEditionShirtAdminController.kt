package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.dto.toValueObject
import org.bialydunajec.registrations.application.query.api.CampEditionShirtQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsQueryGateway
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtId
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.AddCampEditionShirtColorRequest
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.AddCampEditionShirtSizeRequest
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.UpdateCampEditionShirtRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/camp-registrations/camp-shirt")
internal class CampEditionShirtAdminController(
        private val campRegistrationsCommandGateway: CampRegistrationsCommandGateway,
        private val campRegistrationsQueryGateway: CampRegistrationsQueryGateway
) {

    @GetMapping
    fun getCampEditionShirt(@RequestParam campRegistrationsEditionId: String) =
            campRegistrationsQueryGateway.process(CampEditionShirtQuery.ByCampRegistrationsEditionId(campRegistrationsEditionId))


    @PostMapping("/{campEditionShirtId}")
    fun updateCampEditionShirt(@PathVariable campEditionShirtId: String, request: UpdateCampEditionShirtRequest) =
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.UpdateCampEditionShirt(
                            CampEditionShirtId(campEditionShirtId),
                            request.shirtSizesFileUrl?.let { Url(it) }
                    )
            )


    @PostMapping("/{campEditionShirtId}/color")
    fun addCampEditionShirtColorOption(@PathVariable campEditionShirtId: String, request: AddCampEditionShirtColorRequest) =
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.AddCampEditionShirtColorOption(
                            CampEditionShirtId(campEditionShirtId),
                            request.color.toValueObject()
                    )
            )

    @PostMapping("/{campEditionShirtId}/size")
    fun addCampEditionShirtSizeOption(@PathVariable campEditionShirtId: String, request: AddCampEditionShirtSizeRequest) =
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                            CampEditionShirtId(campEditionShirtId),
                            request.size.toValueObject()
                    )
            )


}