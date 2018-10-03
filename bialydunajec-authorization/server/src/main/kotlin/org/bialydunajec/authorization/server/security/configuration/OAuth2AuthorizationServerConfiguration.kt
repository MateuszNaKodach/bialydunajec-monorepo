package org.bialydunajec.authorization.server.security.configuration

import org.bialydunajec.authorization.server.api.dto.UserDetailsDto
import org.bialydunajec.authorization.server.security.OAuth2UserFinder
import org.bialydunajec.authorization.server.security.configuration.properties.BialyDunajecOAuth2Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.stereotype.Service
import javax.sql.DataSource

@EnableAuthorizationServer
@Configuration
internal class OAuth2AuthorizationServerConfiguration(
        private val userDetailsService: UserDetailsService,
        private val oAuth2Properties: BialyDunajecOAuth2Properties,
        private val authenticationManager: AuthenticationManager,
        private val tokenEnhancer: TokenEnhancer,
        private val dataSource: DataSource,
        private val passwordEncoder: PasswordEncoder
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        val inMemoryClientDetailsServiceBuilder = clients.inMemory()
        oAuth2Properties.clients
                .forEach { client ->
                    inMemoryClientDetailsServiceBuilder
                            .withClient(client.clientId)
                            .secret("{noop}" + client.clientSecret)
                            .authorizedGrantTypes(*client.authorizedGrantTypes)
                            .accessTokenValiditySeconds(client.accessTokenValiditySeconds)
                            .refreshTokenValiditySeconds(client.refreshTokenValiditySeconds)
                            .scopes(*client.scopes)
                }
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.passwordEncoder(passwordEncoder)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancer)
                .tokenStore(tokenStore())
                .reuseRefreshTokens(false)
                .userDetailsService(userDetailsService)
        /*.pathMapping("/oauth/token", "rest-api/v1/oauth/token")
        .pathMapping("/oauth/authorize", "rest-api/v1/oauth/authorize")
        .pathMapping("/oauth/check_token", "rest-api/v1/oauth/check_token")
        .pathMapping("/oauth/confirm_access", "rest-api/v1/oauth/confirm_access")
        .pathMapping("/oauth/error", "rest-api/v1/oauth/error");*/
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JdbcTokenStore(dataSource) //Created with tips from: https://javadeveloperzone.com/spring-boot/spring-boot-oauth2-jdbc-token-store-example/
    }
}

@Primary
@Service
internal class UserDetailsServiceImpl(private val oAuth2UserFinder: OAuth2UserFinder) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = UserDetailsDto.createFrom(oAuth2UserFinder.findByUsernameOrEmailAddress(username).getSnapshot())

}