package org.bialydunajec.authorization.server.api

import org.bialydunajec.authorization.server.security.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
internal class AuthorizationServerFacadeConfiguration {

    @Bean
    fun authorizationServerFacade(passwordEncoder: PasswordEncoder, oAuth2UserRepository: OAuth2UserRepository) =
            AuthorizationServerFacade(
                    oAuth2UserCreator = OAuth2UserCreator(oAuth2UserRepository, passwordEncoder),
                    oAuth2UserFinder = OAuth2UserFinder(oAuth2UserRepository),
                    oAuth2UserUpdater = OAuth2UserUpdater(oAuth2UserRepository, passwordEncoder),
                    currentUserGetter = OAuth2CurrentUserGetter(oAuth2UserRepository)
            )

}