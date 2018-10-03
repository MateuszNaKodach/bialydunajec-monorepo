package org.bialydunajec.bootstrap.env.dev

import org.bialydunajec.authorization.server.api.AuthorizationServerFacade
import org.bialydunajec.authorization.server.api.command.CreateUserCredentialsCommand
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
internal class DevEnvironmentBootstrap(val authorizationServerFacade: AuthorizationServerFacade) {

    @PostConstruct
    fun bootstrap() {
        authorizationServerFacade.createUserCredentials(
                CreateUserCredentialsCommand(
                        "kontakt.mateusznowak@gmail.com",
                        "mate96",
                        "test1234"
                )
        )
    }
}