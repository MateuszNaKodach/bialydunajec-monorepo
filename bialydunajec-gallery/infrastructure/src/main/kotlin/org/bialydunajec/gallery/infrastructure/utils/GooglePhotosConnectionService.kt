package org.bialydunajec.gallery.infrastructure.utils

import com.google.auth.oauth2.AccessToken
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.PhotosLibrarySettings
import org.bialydunajec.gallery.infrastructure.utils.pojo.ClientCredentials

class GooglePhotosConnectionService(private val googlePhotosTokenHandler: GooglePhotosTokenHandler,
                                    private val googlePhotosCredentialHandler: GooglePhotosCredentialHandler) {

    private var clientCredentials: ClientCredentials
    private lateinit var accessToken: AccessToken
    private lateinit var photosLibrarySettings: PhotosLibrarySettings

    init {
        clientCredentials = googlePhotosCredentialHandler.readUserCredentialsFormFile()
        setNewApiConnectionCredentials()
    }

    fun initApiConnection(): PhotosLibraryClient {
        if (googlePhotosTokenHandler.isRefreshingTokenRequired(accessToken)) {
            setNewApiConnectionCredentials()
        }
        return PhotosLibraryClient.initialize(photosLibrarySettings)
    }

    private fun setNewApiConnectionCredentials() {
        accessToken = googlePhotosTokenHandler.getNewAccessToken(clientCredentials)
        photosLibrarySettings = googlePhotosCredentialHandler.getPhotosLibrarySettings(clientCredentials, accessToken)
    }
}
