package org.bialydunajec.bootstrap.env.dev

import org.bialydunajec.authorization.server.api.AuthorizationServerFacade
import org.bialydunajec.authorization.server.api.command.CreateUserCredentialsCommand
import org.bialydunajec.authorization.server.api.query.UserByUsernameOrEmailAddressQuery
import org.bialydunajec.configuration.profile.ProfileName.Companion.DEVELOPMENT_ENVIRONMENT
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Profile(DEVELOPMENT_ENVIRONMENT)
@Component
internal class DevEnvironmentBootstrap(val authorizationServerFacade: AuthorizationServerFacade) {

    @PostConstruct
    fun bootstrap() {
        try {
            authorizationServerFacade.findUserByUsernameOrEmailAddress(UserByUsernameOrEmailAddressQuery("mate96"))
        } catch (e: Exception) {
            authorizationServerFacade.createUserCredentials(
                    CreateUserCredentialsCommand(
                            "nmateusz96@gmail.com",
                            "mate96",
                            "test1234"
                    )
            )
        }
    }
}