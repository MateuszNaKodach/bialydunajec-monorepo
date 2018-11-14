package org.bialydunajec.authorization.server.security

import org.bialydunajec.authorization.server.api.dto.UserDetailsDto
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.HashMap

@Primary
@Component
internal class OAuth2TokenEnhancer : TokenEnhancer {

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        addCurrentUserInfoToToken(accessToken as DefaultOAuth2AccessToken, authentication)
        return accessToken
    }

    private fun addCurrentUserInfoToToken(accessToken: DefaultOAuth2AccessToken, authentication: OAuth2Authentication) {
        val currentUser = authentication.principal as UserDetailsDto
        val additionalInfo = HashMap<String, Any>()
        additionalInfo[ATTR_LOGGED_USER] = TokenUserInfoDto(
                userId = currentUser.getUserId(),
                emailAddress = currentUser.getEmailAddress(),
                username = currentUser.username
        )
        accessToken.additionalInformation = additionalInfo
    }

    companion object {
        private const val ATTR_LOGGED_USER = "current_user"
    }

    private data class TokenUserInfoDto(val userId: String, val emailAddress: String, val username: String?) : Serializable
}