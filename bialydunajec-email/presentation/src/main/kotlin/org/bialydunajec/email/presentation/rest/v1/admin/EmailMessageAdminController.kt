package org.bialydunajec.email.presentation.rest.v1.admin

import org.bialydunajec.email.application.api.EmailCommand
import org.bialydunajec.email.application.api.EmailCommandGateway
import org.bialydunajec.email.domain.EmailMessageLogId
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api/v1/admin/email-message")
internal class EmailMessageAdminController(private val commandGateway: EmailCommandGateway) {


    @PostMapping("/{emailMessageLogId}/resend")
    fun resendEmailMessage(@PathVariable emailMessageLogId: String) =
            commandGateway.process(EmailCommand.ResendEmailCommand(EmailMessageLogId(emailMessageLogId)))


}