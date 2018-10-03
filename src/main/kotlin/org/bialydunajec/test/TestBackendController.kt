package org.bialydunajec.test

import org.bialydunajec.authorization.server.api.AuthorizationServerFacade
import org.bialydunajec.authorization.server.api.annotation.CurrentUser
import org.bialydunajec.authorization.server.api.dto.UserDetailsDto
import org.bialydunajec.authorization.server.security.configuration.properties.BialyDunajecOAuth2Properties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api/v1/test-auth")
class TestBackendController(val oAuth2Properties: BialyDunajecOAuth2Properties, val authorizationServerFacade: AuthorizationServerFacade) {

    @GetMapping
    fun getMap() = oAuth2Properties.clients

    @GetMapping("/user-annotation")
    fun getUser(@CurrentUser currentUser: UserDetailsDto?) = currentUser

    @GetMapping("/user-facade")
    fun getUserFacade() = authorizationServerFacade.getCurrentUser()

}