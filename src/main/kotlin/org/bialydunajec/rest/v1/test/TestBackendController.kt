package org.bialydunajec.rest.v1.test

import org.bialydunajec.authorization.server.api.AuthorizationServerFacade
import org.bialydunajec.authorization.server.api.annotation.CurrentUser
import org.bialydunajec.authorization.server.api.dto.UserDetailsDto
import org.bialydunajec.authorization.server.security.configuration.properties.BialyDunajecOAuth2Properties
import org.bialydunajec.ddd.application.base.email.EmailMessage
import org.bialydunajec.ddd.application.base.email.EmailMessageSender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api/v1/test-auth")
class TestBackendController(
        val oAuth2Properties: BialyDunajecOAuth2Properties,
        val authorizationServerFacade: AuthorizationServerFacade,
        val emailSender: EmailMessageSender) {

    @GetMapping
    fun getMap() = oAuth2Properties.clients

    @GetMapping("/user-annotation")
    fun getUser(@CurrentUser currentUser: UserDetailsDto?) = currentUser

    @GetMapping("/user-facade")
    fun getUserFacade() = authorizationServerFacade.getCurrentUser()

    @GetMapping("/send-email")
    fun sendTestEmail() = emailSender.sendEmailMessage(
            EmailMessage(
                    EmailAddress("nmateusz96@gmail.com"),
                    "Testowa wiadomość",
                    "<b>Content</b> Testowy!!! <div>Lala</div>"
            )
    )

}