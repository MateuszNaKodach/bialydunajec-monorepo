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
import org.bialydunajec.gallery.infrastructure.utils.pojo.ClientCredentials
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

internal class GooglePhotosCredentialService(private val refreshTokenValue: String,
                                             private val credentialsJsonPath: String) {

    private var clientCredentials: ClientCredentials
    private lateinit var accessToken: AccessToken
    private lateinit var photosLibrarySettings: PhotosLibrarySettings

    private val HTTP_TRANSPORT: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private val JSON_FACTORY: JsonFactory = JacksonFactory()
    private val log: Logger = LoggerFactory.getLogger(GooglePhotosCredentialService::class.java)

    init {
        clientCredentials = readUserCredentialsFormFile()
        setNewApiConnectionCredentials()
    }

    fun initApiConnection(): PhotosLibraryClient {
        if (isRefreshingTokenRequired()) {
            setNewApiConnectionCredentials()
        }
        return PhotosLibraryClient.initialize(photosLibrarySettings)
    }

    private fun readUserCredentialsFormFile(): ClientCredentials {
        val gson = Gson()
        val bufferedReader: BufferedReader = File(credentialsJsonPath).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        return gson.fromJson(inputString, ClientCredentials::class.java)
    }

    private fun setNewApiConnectionCredentials() {
        accessToken = getNewAccessToken()
        photosLibrarySettings = getPhotosLibrarySettings()
    }

    private fun getPhotosLibrarySettings(): PhotosLibrarySettings =
            PhotosLibrarySettings
                    .newBuilder()
                    .setCredentialsProvider(
                            FixedCredentialsProvider.create(
                                    getCredentials()
                            )
                    ).build()

    private fun getCredentials(): Credentials =
            UserCredentials.newBuilder()
                    .setClientId(clientCredentials.web!!.clientId)
                    .setClientSecret(clientCredentials.web!!.clientSecret)
                    .setAccessToken(accessToken)
                    .setRefreshToken(refreshTokenValue)
                    .build()

    private fun getNewAccessToken(): AccessToken {
        val tokenResponse = refreshToken(refreshTokenValue)
        return AccessToken(
                tokenResponse!!.accessToken,
                setAccessTokenExpirationDate(tokenResponse.expiresInSeconds)
        )
    }

    // TODO: add time margin
    private fun isRefreshingTokenRequired(): Boolean =
            accessToken.tokenValue.isNullOrEmpty()
                    || Date() > accessToken.expirationTime

    private fun setAccessTokenExpirationDate(expiresInSeconds: Long): Date {
        val now = Date()
        return Date(now.time
                .plus(TimeUnit.SECONDS.toMillis(expiresInSeconds)))
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
                    GenericUrl(clientCredentials.web!!.tokenUri),
                    refreshTokenValue
            ).setClientAuthentication(
                    ClientParametersAuthentication(
                            clientCredentials.web!!.clientId,
                            clientCredentials.web!!.clientSecret
                    )
            ).execute()
}
