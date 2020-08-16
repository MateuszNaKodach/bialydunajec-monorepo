package org.bialydunajec.email.presentation.rest.v1.admin

import org.bialydunajec.email.application.api.EmailGroupCommand
import org.bialydunajec.email.application.api.EmailGroupCommandGateway
import org.bialydunajec.email.domain.EmailGroupId
import org.bialydunajec.email.domain.valueobject.EmailGroupName
import org.bialydunajec.email.presentation.rest.v1.admin.request.ChangeEmailGroupNameRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/email-group")
internal class EmailGroupAdminController(private val commandGateway: EmailGroupCommandGateway) {


    @PutMapping("/{emailGroupId}")
    fun resendEmailMessage(@PathVariable emailGroupId: String, @RequestBody body: ChangeEmailGroupNameRequest) =
        commandGateway.process(
            EmailGroupCommand.ChangeEmailGroupName(
                EmailGroupId(emailGroupId),
                EmailGroupName(body.newName)
            )
        )


}
