package org.bialydunajec.gallery.infrastructure.utils

import com.google.api.client.auth.oauth2.ClientParametersAuthentication
import com.google.api.client.auth.oauth2.RefreshTokenRequest
import com.google.api.client.auth.oauth2.TokenResponse
import com.google.api.client.auth.oauth2.TokenResponseException
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.auth.oauth2.AccessToken
import org.bialydunajec.gallery.infrastructure.utils.pojo.ClientCredentials
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.TimeUnit

class GooglePhotosTokenHandler(private val refreshTokenValue: String) {

    private val HTTP_TRANSPORT: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private val JSON_FACTORY: JsonFactory = JacksonFactory()
    private val log: Logger = LoggerFactory.getLogger(GooglePhotosConnectionService::class.java)

    fun getNewAccessToken(clientCredentials: ClientCredentials): AccessToken {
        val tokenResponse = refreshToken(clientCredentials)
        return AccessToken(
                tokenResponse!!.accessToken,
                setAccessTokenExpirationDate(tokenResponse.expiresInSeconds)
        )
    }

    // TODO: add time margin
    fun isRefreshingTokenRequired(accessToken: AccessToken): Boolean =
            accessToken.tokenValue.isNullOrEmpty()
                    || Date() > accessToken.expirationTime

    private fun setAccessTokenExpirationDate(expiresInSeconds: Long): Date {
        val now = Date()
        return Date(now.time
                .plus(TimeUnit.SECONDS.toMillis(expiresInSeconds)))
    }

    private fun refreshToken(clientCredentials: ClientCredentials): TokenResponse? =
            try {
                tryRefreshToken(clientCredentials)
            } catch (e: TokenResponseException) {
                log.error("Error during refreshing Google API token: ", e)
                null
            }

    private fun tryRefreshToken(clientCredentials: ClientCredentials) =
            RefreshTokenRequest(
                    HTTP_TRANSPORT, JSON_FACTORY,
                    GenericUrl(clientCredentials.web!!.tokenUri),
                    refreshTokenValue
            ).setClientAuthentication(
                    ClientParametersAuthentication(
                            clientCredentials.web!!.clientId,
                            clientCredentials.web!!.clientSecret
                    )
            ).execute()
}
