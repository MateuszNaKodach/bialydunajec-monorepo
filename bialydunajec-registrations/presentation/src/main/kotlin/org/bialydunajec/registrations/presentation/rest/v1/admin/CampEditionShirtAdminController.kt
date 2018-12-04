package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.dto.toValueObject
import org.bialydunajec.registrations.application.query.api.CampEditionShirtQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsQueryGateway
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/camp-registrations/camp-shirt")
internal class CampEditionShirtAdminController(
        private val campRegistrationsCommandGateway: CampRegistrationsAdminCommandGateway,
        private val campRegistrationsQueryGateway: CampRegistrationsQueryGateway
) {

    @GetMapping
    fun getCampEditionShirt(@RequestParam campRegistrationsEditionId: String) =
            campRegistrationsQueryGateway.process(CampEditionShirtQuery.ByCampRegistrationsEditionId(campRegistrationsEditionId))

    @GetMapping("/order")
    fun getCampEditionShirtOrdersByCampRegistrationsEditionId(@RequestParam campRegistrationsEditionId: String) =
            campRegistrationsQueryGateway.process(CampEditionShirtQuery.ByCampRegistrationsEditionId(campRegistrationsEditionId))


    @PostMapping("/{campEditionShirtId}")
    fun updateCampEditionShirt(@PathVariable campEditionShirtId: String, @RequestBody request: UpdateCampEditionShirtRequest) =
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.UpdateCampEditionShirt(
                            CampEditionShirtId(campEditionShirtId),
                            request.shirtSizesFileUrl?.let { Url.ExternalUrl(it) }
                    )
            )


    @PostMapping("/{campEditionShirtId}/color")
    fun addCampEditionShirtColorOption(@PathVariable campEditionShirtId: String, @RequestBody request: AddCampEditionShirtColorRequest) =
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.AddCampEditionShirtColorOption(
                            CampEditionShirtId(campEditionShirtId),
                            request.color.toValueObject(),
                            request.available
                    )
            )

    @PutMapping("/{campEditionShirtId}/color/{shirtColorOptionId}")
    fun updateCampEditionShirtColorOption(
            @PathVariable campEditionShirtId: String,
            @PathVariable shirtColorOptionId: String,
            @RequestBody request: UpdateCampEditionShirtColorOptionRequest) =
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.UpdateCampEditionShirtColorOption(
                            CampEditionShirtId(campEditionShirtId),
                            ShirtColorOptionId(shirtColorOptionId),
                            request.color.toValueObject(),
                            request.available
                    )
            )

    @PostMapping("/{campEditionShirtId}/size")
    fun addCampEditionShirtSizeOption(@PathVariable campEditionShirtId: String, @RequestBody request: AddCampEditionShirtSizeRequest) =
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                            CampEditionShirtId(campEditionShirtId),
                            request.size.toValueObject(),
                            request.available
                    )
            )

    @PutMapping("/{campEditionShirtId}/size/{shirtSizeOptionId}")
    fun updateCampEditionShirtSizeOption(
            @PathVariable campEditionShirtId: String,
            @PathVariable shirtSizeOptionId: String,
            @RequestBody request: UpdateCampEditionShirtSizeOptionRequest) =
            campRegistrationsCommandGateway.process(
                    CampRegistrationsCommand.UpdateCampEditionShirtSizeOption(
                            CampEditionShirtId(campEditionShirtId),
                            ShirtSizeOptionId(shirtSizeOptionId),
                            request.size.toValueObject(),
                            request.available
                    )
            )


}