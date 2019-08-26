package org.bialydunajec.gallery.infrastructure.utils

import com.google.api.client.auth.oauth2.ClientParametersAuthentication
import com.google.api.client.auth.oauth2.RefreshTokenRequest
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
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.PhotosLibrarySettings
import org.slf4j.Logger
import org.slf4j.LoggerFactory


internal class GooglePhotosCredentialService {

    companion object {
        private const val REFRESH_TOKEN: String = "1/poWoHFGQd2ppMsJr9XZTxVHl6JvvrfiGnvuc2JoREe4"
        private const val CLIENT_ID: String = "449611120307-br5s5t3l6r329i6pk0tcr067jbs8f942.apps.googleusercontent.com"
        private const val CLIENT_SECRET: String = "u3oTMeQSDe0ZpprgPtUuKAv9"

        private val HTTP_TRANSPORT: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
        private val JSON_FACTORY: JsonFactory = JacksonFactory()

        private val log: Logger = LoggerFactory.getLogger(this::class.java)

        fun initApiConnection(): PhotosLibraryClient {
            val credentials: Credentials = getCredentials()

            val settings: PhotosLibrarySettings = PhotosLibrarySettings
                    .newBuilder()
                    .setCredentialsProvider(
                            FixedCredentialsProvider.create(credentials)
                    ).build()

            return PhotosLibraryClient.initialize(settings)
        }

        private fun getCredentials(): Credentials {
            val accessToken = AccessToken(refreshToken(), null)
            return UserCredentials.newBuilder()
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    .setAccessToken(accessToken)
                    .setRefreshToken(REFRESH_TOKEN)
                    .build()
        }

        private fun refreshToken(): String? =
                try {
                    tryRefreshToken().accessToken
                } catch (e: TokenResponseException) {
                    log.error("Error during refreshing Google API token: ", e)
                    null
                }

        private fun tryRefreshToken() =
                RefreshTokenRequest(HTTP_TRANSPORT,
                        JSON_FACTORY,
                        GenericUrl("https://oauth2.googleapis.com/token"),
                        REFRESH_TOKEN)
                        .setClientAuthentication(
                                ClientParametersAuthentication(CLIENT_ID, CLIENT_SECRET))
                        .execute()
    }
}
