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
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.Credentials
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.UserCredentials
import com.google.gson.Gson
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.PhotosLibrarySettings
import org.bialydunajec.gallery.infrastructure.utils.pojo.ClientCredentialsPojo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.File
import java.util.*

internal class GooglePhotosCredentialService {

    companion object {
        private lateinit var clientCredentialsPojo: ClientCredentialsPojo
        private lateinit var accessToken: AccessToken
        private lateinit var photosLibrarySettings: PhotosLibrarySettings

        private val HTTP_TRANSPORT: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
        private val JSON_FACTORY: JsonFactory = JacksonFactory()

        private val log: Logger = LoggerFactory.getLogger(this::class.java)

        fun initApiConnection(refreshTokenValue: String, credentialsJsonPath: String): PhotosLibraryClient {
            if(!::clientCredentialsPojo.isInitialized)
                readUserCredentialsFormFile(credentialsJsonPath)

            resetPhotosLibrarySettingsIfRequired(refreshTokenValue)

            return PhotosLibraryClient.initialize(photosLibrarySettings)
        }

        private fun readUserCredentialsFormFile(credentialsJsonPath: String) {
            val gson = Gson()
            val bufferedReader: BufferedReader = File(credentialsJsonPath).bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            this.clientCredentialsPojo = gson.fromJson(inputString, ClientCredentialsPojo::class.java)
        }

        private fun resetPhotosLibrarySettingsIfRequired(refreshTokenValue: String) {
            if ( isRefreshingTokenRequired() || !::photosLibrarySettings.isInitialized) {
                val credentials = getCredentials(refreshTokenValue)
                photosLibrarySettings = PhotosLibrarySettings
                        .newBuilder()
                        .setCredentialsProvider(
                                FixedCredentialsProvider.create(credentials)
                        ).build()
            }
        }

        private fun getCredentials(refreshTokenValue: String): Credentials {
            val tokenResponse = refreshToken(refreshTokenValue)
            accessToken = AccessToken(
                    tokenResponse!!.accessToken,
                    setAccessTokenExpirationDate(tokenResponse.expiresInSeconds)
            )

            return UserCredentials.newBuilder()
                    .setClientId(clientCredentialsPojo.web!!.clientId)
                    .setClientSecret(clientCredentialsPojo.web!!.clientSecret)
                    .setAccessToken(accessToken)
                    .setRefreshToken(refreshTokenValue)
                    .build()
        }
        // TODO: add time margin
        private fun isRefreshingTokenRequired(): Boolean =
                !::accessToken.isInitialized
                        || accessToken.tokenValue.isNullOrEmpty()
                        || Date() > accessToken.expirationTime

        private fun setAccessTokenExpirationDate(expiresInMilliseconds: Long): Date {
            val now = Date()
            return Date(now.time.plus(expiresInMilliseconds))
        }

        private fun refreshToken(refreshTokenValue: String): TokenResponse? =
                try {
                    tryRefreshToken(refreshTokenValue)
                } catch (e: TokenResponseException) {
                    log.error("Error during refreshing Google API token: ", e)
                    null
                }

        private fun tryRefreshToken(refreshTokenValue: String) =
                RefreshTokenRequest(
                        HTTP_TRANSPORT, JSON_FACTORY,
                        GenericUrl(clientCredentialsPojo.web!!.tokenUri),
                        refreshTokenValue
                ).setClientAuthentication(
                        ClientParametersAuthentication(
                                clientCredentialsPojo.web!!.clientId,
                                clientCredentialsPojo.web!!.clientSecret
                        )
                ).execute()
    }
}
