package org.bialydunajec.email.presentation.rest.v1.admin

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.application.api.EmailCommand
import org.bialydunajec.email.application.api.EmailCommandGateway
import org.bialydunajec.email.domain.EmailGroupId
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import org.bialydunajec.email.presentation.rest.v1.admin.request.CatagolizeEmailRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/email")
internal class EmailAdminController(private val commandGateway: EmailCommandGateway) {


    @PostMapping
    fun catagolizeEmail(@PathVariable emailGroupId: String, @RequestBody body: CatagolizeEmailRequest) =
        commandGateway.process(
            EmailCommand.CatalogizeEmail(
                EmailAddress(body.emailAddress),
                body.emailGroupId?.let { EmailGroupId(it) },
                EmailAddressOwner(FirstName(body.owner.firstName), LastName(body.owner.lastName))
            )
        )

}
