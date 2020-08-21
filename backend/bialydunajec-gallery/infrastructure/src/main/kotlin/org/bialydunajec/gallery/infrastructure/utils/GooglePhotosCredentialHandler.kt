package org.bialydunajec.gallery.infrastructure.utils

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.Credentials
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.UserCredentials
import com.google.gson.Gson
import com.google.photos.library.v1.PhotosLibrarySettings
import org.bialydunajec.gallery.infrastructure.utils.pojo.ClientCredentials
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.File

class GooglePhotosCredentialHandler(private val credentialsJsonPath: String,
                                    private val refreshTokenValue: String) {

    fun readUserCredentialsFormFile(): ClientCredentials {
        val gson = Gson()
        val bufferedReader: BufferedReader = ClassPathResource(credentialsJsonPath).inputStream.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        return gson.fromJson(inputString, ClientCredentials::class.java)
    }

    fun getPhotosLibrarySettings(clientCredentials: ClientCredentials, accessToken: AccessToken): PhotosLibrarySettings =
            PhotosLibrarySettings
                    .newBuilder()
                    .setCredentialsProvider(
                            FixedCredentialsProvider.create(
                                    getUserCredentials(clientCredentials, accessToken)
                            )
                    ).build()

    private fun getUserCredentials(clientCredentials: ClientCredentials, accessToken: AccessToken): Credentials =
            UserCredentials.newBuilder()
                    .setClientId(clientCredentials.web!!.clientId)
                    .setClientSecret(clientCredentials.web!!.clientSecret)
                    .setAccessToken(accessToken)
                    .setRefreshToken(refreshTokenValue)
                    .build()
}
