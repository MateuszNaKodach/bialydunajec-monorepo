package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.ddd.application.base.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.application.dto.toValueObject
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.UpdateCottageRequest
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/rest-api/v1/admin/camp-registrations")
class CottageAdminController(
    private val commandGateway: CampRegistrationsAdminCommandGateway,
    private val queryGateway: CampRegistrationsQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PostMapping("/cottage/academic-ministry-cottage")
    fun createAcademicMinistryCottage(@RequestParam campRegistrationsEditionId: Int, @RequestParam academicMinistryId: String) =
        commandGateway.process(
            CampRegistrationsCommand.CreateAcademicMinistryCottage(
                campRegistrationsEditionId,
                academicMinistryId
            )
        )


    @PostMapping("/cottage/standalone-cottage")
    fun createStandaloneCottage(@RequestParam campRegistrationsEditionId: Int, @RequestParam cottageName: String) =
        commandGateway.process(
            CampRegistrationsCommand.CreateStandaloneCottage(
                campRegistrationsEditionId,
                cottageName
            )
        )

    @PutMapping("/cottage/{cottageId}")
    fun updateCottage(@PathVariable cottageId: String, @Valid @RequestBody request: UpdateCottageRequest) =
        commandGateway.process(
            CampRegistrationsCommand.UpdateCottage(
                cottageId = CottageId(cottageId),
                name = request.name,
                logoImageUrl = request.logoImageUrl?.let { Url.ExternalUrl(it) },
                buildingPhotoUrl = request.buildingPhotoUrl?.let { Url.ExternalUrl(it) },
                place = request.place?.toValueObject(),
                cottageSpace = request.cottageSpace.toValueObject(),
                campersLimitations = request.campersLimitations?.toValueObject(),
                bankTransferDetails = request.bankTransferDetails?.toValueObject(),
                cottageBoss = request.cottageBoss?.toValueObject()
            )
        )

    @PatchMapping("/cottage/{cottageId}/activate")
    fun activateCottage(@PathVariable cottageId: String) =
        commandGateway.process(CampRegistrationsCommand.ActivateCottage(cottageId = CottageId(cottageId)))

    @PatchMapping("/cottage/{cottageId}/deactivate")
    fun deactivateCottage(@PathVariable cottageId: String) =
        commandGateway.process(CampRegistrationsCommand.DeactivateCottage(cottageId = CottageId(cottageId)))

    @DeleteMapping("/cottage/{cottageId}")
    fun deleteCottage(@PathVariable cottageId: String) =
        commandGateway.process(CampRegistrationsCommand.DeleteCottage(cottageId = CottageId(cottageId)))

    //QUERY------------------------------------------------------------------------------------------------------------
    @GetMapping("/cottage")
    fun getAllCottagesByCampRegistrationsEditionId(@RequestParam campRegistrationsEditionId: String) =
        queryGateway.process(CottageQuery.AllByCampRegistrationsEditionId(campRegistrationsEditionId))
            .sortedBy { it.name }

    @GetMapping("/cottage/{cottageId}")
    fun getByIdAndCampRegistrationsEditionId(@RequestParam campRegistrationsEditionId: String, @PathVariable cottageId: String) =
        queryGateway.process(CottageQuery.ByIdAndByCampRegistrationsEditionId(cottageId, campRegistrationsEditionId))

}
