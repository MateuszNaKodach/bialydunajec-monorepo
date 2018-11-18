package org.bialydunajec.bootstrap.env.dev

import org.bialydunajec.authorization.server.api.AuthorizationServerFacade
import org.bialydunajec.authorization.server.api.command.CreateUserCredentialsCommand
import org.bialydunajec.authorization.server.api.query.UserByUsernameOrEmailAddressQuery
import org.bialydunajec.configuration.profile.ProfileName.Companion.DEVELOPMENT_ENVIRONMENT
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*
import javax.annotation.PostConstruct


@Profile(DEVELOPMENT_ENVIRONMENT)
@Component
internal class DevEnvironmentBootstrap(val authorizationServerFacade: AuthorizationServerFacade) {

    @PostConstruct
    fun bootstrap() {

    }
}