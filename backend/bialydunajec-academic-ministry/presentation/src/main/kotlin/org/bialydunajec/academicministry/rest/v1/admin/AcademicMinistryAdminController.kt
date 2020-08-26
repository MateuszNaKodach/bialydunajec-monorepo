package org.bialydunajec.academicministry.rest.v1.admin

import org.bialydunajec.academicministry.application.command.api.AcademicMinistryCommand
import org.bialydunajec.academicministry.application.command.api.AcademicMinistryAdminCommandGateway
import org.bialydunajec.academicministry.application.dto.toValueObject
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQuery
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryAdminQueryGateway
import org.bialydunajec.academicministry.domain.AcademicMinistryId
import org.bialydunajec.academicministry.domain.entity.AcademicPriestId
import org.bialydunajec.academicministry.rest.v1.admin.request.CreateAcademicPriestRequest
import org.bialydunajec.academicministry.rest.v1.admin.request.CreateAcademicMinistryRequest
import org.bialydunajec.academicministry.rest.v1.admin.request.UpdateAcademicMinistryRequest
import org.bialydunajec.ddd.application.base.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/academic-ministry")
internal class AcademicMinistryAdminController(
        private val academicMinistryAdminCommandGateway: AcademicMinistryAdminCommandGateway,
        private val academicMinistryAdminQueryGateway: AcademicMinistryAdminQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAcademicMinistry(@RequestBody request: CreateAcademicMinistryRequest) =
            academicMinistryAdminCommandGateway.process(
                    AcademicMinistryCommand.CreateAcademicMinistry(
                            officialName = request.officialName,
                            shortName = request.shortName,
                            logoImageUrl = request.logoImageUrl?.let { Url.ExternalUrl(it) },
                            place = request.place?.toValueObject(),
                            socialMedia = request.socialMedia.toValueObject(),
                            emailAddress = request.emailAddress?.let { EmailAddress(it) },
                            photoUrl = request.photoUrl?.let { Url.ExternalUrl(it) },
                            description = request.description?.toValueObject()
                    )
            )

    @PutMapping("/{academicMinistryId}")
    fun updateAcademicMinistry(@PathVariable academicMinistryId: String, @RequestBody request: UpdateAcademicMinistryRequest) =
            academicMinistryAdminCommandGateway.process(
                    AcademicMinistryCommand.UpdateAcademicMinistry(
                            academicMinistryId = AcademicMinistryId(academicMinistryId),
                            officialName = request.officialName,
                            shortName = request.shortName,
                            logoImageUrl = request.logoImageUrl?.let { Url.ExternalUrl(it) },
                            place = request.place?.toValueObject(),
                            socialMedia = request.socialMedia.toValueObject(),
                            emailAddress = request.emailAddress?.let { EmailAddress(it) },
                            photoUrl = request.photoUrl?.let { Url.ExternalUrl(it) },
                            description = request.description?.toValueObject()
                    )
            )

    @PostMapping("/{academicMinistryId}/priest")
    fun createAcademicMinistryPriest(@PathVariable academicMinistryId: String, @RequestBody request: CreateAcademicPriestRequest) =
            with(request) {
                academicMinistryAdminCommandGateway.process(
                        AcademicMinistryCommand.CreateAcademicMinistryPriest(
                                academicMinistryId = AcademicMinistryId(academicMinistryId),
                                firstName = FirstName(firstName),
                                lastName = LastName(lastName),
                                personalTitle = personalTitle?.toValueObject(),
                                emailAddress = emailAddress?.let { EmailAddress(it) },
                                phoneNumber = phoneNumber?.let { PhoneNumber(it) },
                                description = description?.toValueObject(),
                                photoUrl = photoUrl?.let { Url.ExternalUrl(it) }
                        )
                )
            }

    @DeleteMapping("/{academicMinistryId}/priest/{academicPriestId}")
    fun removeAcademicMinistryPriest(@PathVariable academicMinistryId: String, @PathVariable academicPriestId: String) =
                academicMinistryAdminCommandGateway.process(
                        AcademicMinistryCommand.RemoveAcademicMinistryPriest(
                                AcademicMinistryId(academicMinistryId),
                                AcademicPriestId(academicPriestId)
                        )
                )


    //QUERY-------------------------------------------------------------------------------------------------------------
    @GetMapping
    fun getAllAcademicMinistries() =
            academicMinistryAdminQueryGateway.process(AcademicMinistryQuery.All)
                    .sortedBy { it.getDisplayName() }


    @GetMapping("/{academicMinistryId}")
    fun getAcademicMinistryById(@PathVariable academicMinistryId: String) =
            academicMinistryAdminQueryGateway.process(AcademicMinistryQuery.ById(academicMinistryId))

    @GetMapping("/{academicMinistryId}/priest")
    fun getAllAcademicPriestByAcademicMinistryId(@PathVariable academicMinistryId: String) =
            academicMinistryAdminQueryGateway.process(AcademicMinistryQuery.AllPriestsByAcademicMinistryId(academicMinistryId))
}
