package org.bialydunajec.configuration.auditing

import org.bialydunajec.authorization.server.api.AuthorizationServerFacade
import org.bialydunajec.authorization.server.security.OAuth2UserId
import org.bialydunajec.authorization.server.security.OAuth2UserRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing.Auditor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", modifyOnCreate = false)
internal class JpaAuditingConfiguration(private val authorizationServerFacade: AuthorizationServerFacade) {

    @Bean
    fun auditorAware(): AuditorAware<Auditor> = AuditorAware<Auditor> {
        val currentUserId = authorizationServerFacade.getCurrentUser()
        Optional.ofNullable(currentUserId?.let { Auditor(it.getUserId()) })
    }

}