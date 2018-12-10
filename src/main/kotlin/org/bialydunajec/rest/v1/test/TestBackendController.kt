package org.bialydunajec.rest.v1.test

import org.bialydunajec.authorization.server.api.AuthorizationServerFacade
import org.bialydunajec.authorization.server.api.annotation.CurrentUser
import org.bialydunajec.authorization.server.api.dto.UserDetailsDto
import org.bialydunajec.authorization.server.security.configuration.properties.BialyDunajecOAuth2Properties
import org.bialydunajec.ddd.application.base.email.EmailMessageSenderPort
import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api/v1/test-auth")
class TestBackendController(
        val oAuth2Properties: BialyDunajecOAuth2Properties,
        val authorizationServerFacade: AuthorizationServerFacade,
        val emailSender: EmailMessageSenderPort) {

    @GetMapping
    fun getMap() = oAuth2Properties.clients

    @GetMapping("/user-annotation")
    fun getUser(@CurrentUser currentUser: UserDetailsDto?) = currentUser

    @GetMapping("/user-facade")
    fun getUserFacade() = authorizationServerFacade.tryGetCurrentUser()

    @GetMapping("/email")
    fun sendEmail() {
        val emailMessage =
                SimpleEmailMessage(
                        EmailAddress("bialydunajec@niepodam.pl"),
                        "Obóz w Białym Dunajcu",
                        """Cześć, testowy mail:"""
                )
        emailSender.sendEmailMessage(emailMessage)
    }

    @GetMapping("/email-bad")
    fun sendBadEmail() {
        val emailMessage =
                SimpleEmailMessage(
                        EmailAddress("bialydunajecqwerer"),
                        "Obóz w Białym Dunajcu",
                        """Cześć, testowy mail:"""
                )
        emailSender.sendEmailMessage(emailMessage)
    }
}