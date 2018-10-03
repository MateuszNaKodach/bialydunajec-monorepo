package org.bialydunajec.authorization.server.security

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Component

@Component
internal class PasswordEncoderProvider  {

    @Bean
    fun passwordEncoder() = PasswordEncoderFactories.createDelegatingPasswordEncoder();
}