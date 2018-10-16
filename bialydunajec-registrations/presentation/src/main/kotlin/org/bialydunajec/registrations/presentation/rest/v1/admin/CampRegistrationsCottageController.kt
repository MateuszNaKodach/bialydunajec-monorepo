package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.ddd.application.base.query.api.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.dto.CottageDto
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.application.dto.toValueObject
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.UpdateCottageRequest
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/rest-api/v1/camp-registrations")
class CampRegistrationsCottageController(
        private val commandGateway: CampRegistrationsCommandGateway,
        private val queryGateway: CampRegistrationsQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PostMapping("/{campRegistrationsEditionId}/cottage/academic-ministry-cottage")
    fun createAcademicMinistryCottage(@PathVariable campRegistrationsEditionId: Int, @RequestParam academicMinistryId: String) =
            commandGateway.process(CampRegistrationsCommand.CreateAcademicMinistryCottage(campRegistrationsEditionId, academicMinistryId))


    @PostMapping("/{campRegistrationsEditionId}/cottage/standalone-cottage")
    fun createStandaloneCottage(@PathVariable campRegistrationsEditionId: Int, @RequestParam cottageName: String) =
            commandGateway.process(CampRegistrationsCommand.CreateStandaloneCottage(campRegistrationsEditionId, cottageName))

    @PutMapping("/{campRegistrationsEditionId}/cottage/{cottageId}")
    fun updateCottage(@PathVariable campRegistrationsEditionId: Int, @PathVariable cottageId: String, @Valid @RequestBody request: UpdateCottageRequest) =
            commandGateway.process(
                    CampRegistrationsCommand.UpdateCottage(
                            cottageId = CottageId(cottageId),
                            name = request.name,
                            logoImageUrl = request.logoImageUrl?.let { Url(it) },
                            buildingPhotoUrl = request.buildingPhotoUrl?.let { Url(it) },
                            place = request.place?.toValueObject(),
                            cottageSpace = request.cottageSpace.toValueObject(),
                            campersLimitations = request.campersLimitations?.toValueObject(),
                            bankTransferDetails = request.bankTransferDetails?.toValueObject()
                    )
            )

    @PatchMapping("/{campRegistrationsEditionId}/cottage/{cottageId}/activate")
    fun activateCottage(@PathVariable campRegistrationsEditionId: Int, @PathVariable cottageId: String) =
            commandGateway.process(CampRegistrationsCommand.ActivateCottage(cottageId = CottageId(cottageId)))

    @PatchMapping("/{campRegistrationsEditionId}/cottage/{cottageId}/deactivate")
    fun deactivateCottage(@PathVariable campRegistrationsEditionId: Int, @PathVariable cottageId: String) =
            commandGateway.process(CampRegistrationsCommand.DeactivateCottage(cottageId = CottageId(cottageId)))

    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping("/{campRegistrationsEditionId}/cottage")
    fun getAllCottagesByCampRegistrationsEditionId(@PathVariable campRegistrationsEditionId: String) =
            queryGateway.process(CottageQuery.AllByCampRegistrationsEditionId(campRegistrationsEditionId))
                    .sortedBy { it.name }

    @GetMapping("/{campRegistrationsEditionId}/cottage/{cottageId}")
    fun getByIdAndCampRegistrationsEditionId(@PathVariable campRegistrationsEditionId: String, @PathVariable cottageId: String) =
            queryGateway.process(CottageQuery.ByIdAndByCampRegistrationsEditionId(cottageId, campRegistrationsEditionId))

}