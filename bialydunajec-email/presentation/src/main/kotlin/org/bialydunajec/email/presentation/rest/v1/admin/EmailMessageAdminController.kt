package org.bialydunajec.email.presentation.rest.v1.admin

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.application.api.EmailCommand
import org.bialydunajec.email.application.api.EmailCommandGateway
import org.bialydunajec.email.domain.EmailMessageLogId
import org.bialydunajec.email.domain.valueobject.EmailMessage
import org.bialydunajec.email.presentation.rest.v1.admin.request.ForwardEmailMessageRequest
import org.bialydunajec.email.presentation.rest.v1.admin.request.SendEmailMessageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/email-message")
internal class EmailMessageAdminController(private val commandGateway: EmailCommandGateway) {

    @PostMapping
    fun sendEmailMessage(@RequestBody body: SendEmailMessageRequest) =
            with(body) {
                emailAddresses.toSet().forEach {
                    commandGateway.process(
                            EmailCommand.SendEmailCommand(
                                    EmailMessage(
                                            EmailAddress(it),
                                            subject,
                                            content,
                                            EmailMessageLogId()
                                    )
                            )
                    )
                }
            }


    @PutMapping("/{emailMessageLogId}/resend")
    fun resendEmailMessage(@PathVariable emailMessageLogId: String) =
            commandGateway.process(EmailCommand.ResendEmailCommand(EmailMessageLogId(emailMessageLogId)))

    @PostMapping("/{emailMessageLogId}/forward")
    fun duplicateEmailMessage(@PathVariable emailMessageLogId: String, @RequestBody body: ForwardEmailMessageRequest) =
            commandGateway.process(
                    EmailCommand.ForwardEmailCommand(
                            EmailMessageLogId(emailMessageLogId),
                            body.emailAddresses.map { EmailAddress(it) }.toSet())
            )


}