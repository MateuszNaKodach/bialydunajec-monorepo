package org.bialydunajec.gallery.infrastructure.utils

import com.google.auth.oauth2.AccessToken
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.PhotosLibrarySettings
import io.mockk.*
import org.bialydunajec.gallery.infrastructure.utils.pojo.ClientCredentials
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

//TODO: fix "The Application Default Credentials are not available" on WHEN sections
object GooglePhotosConnectionServiceTest: Spek({
    Feature("Initialization of connection to API") {
        val googlePhotosCredentialHandler by memoized { mockk<GooglePhotosCredentialHandler>(relaxed = true) }
        val clientCredentials  = ClientCredentials()
        val googlePhotosTokenHandler by memoized { mockk<GooglePhotosTokenHandler>(relaxed = true) }
        val accessToken = AccessToken("token", null)
        val photosLibrarySettings = mockk<PhotosLibrarySettings>()

        val googlePhotosConnectionService
                by memoized { GooglePhotosConnectionService(googlePhotosTokenHandler, googlePhotosCredentialHandler) }
        mockkStatic(PhotosLibraryClient::class)
        mockkConstructor(PhotosLibraryClient::class)

        Scenario("Successfully connect without refreshing token") {
            Given("init") {
                every { googlePhotosCredentialHandler.readUserCredentialsFormFile() } returns clientCredentials
                every { googlePhotosTokenHandler.getNewAccessToken(clientCredentials) } returns accessToken
                every { googlePhotosCredentialHandler.getPhotosLibrarySettings(clientCredentials, accessToken) } returns photosLibrarySettings
                every { PhotosLibraryClient.initialize(any()) } answers { PhotosLibraryClient.create() as PhotosLibraryClient? }
            }
            Given("Access token is not expired") {
                every { googlePhotosTokenHandler
                        .isRefreshingTokenRequired(accessToken) } returns false
            }

            When("Connecting") {
                googlePhotosConnectionService.initApiConnection()
            }

            Then("Connection data should be set only once in init block") {
                verify(exactly = 1) {
                    googlePhotosTokenHandler.isRefreshingTokenRequired(accessToken)
                    googlePhotosTokenHandler.getNewAccessToken(clientCredentials)
                    googlePhotosCredentialHandler.getPhotosLibrarySettings(clientCredentials, accessToken)
                }
            }
            And("Client credentials should be red from file") {
                verify(exactly = 1) { googlePhotosCredentialHandler.readUserCredentialsFormFile() }
            }
            And("Connections should be initialized") {
                verify { PhotosLibraryClient.initialize(photosLibrarySettings) }
            }
            And("No more interaction with token and credentials handlers") {
                confirmVerified(googlePhotosTokenHandler, googlePhotosCredentialHandler)
            }
        }

        Scenario("Successfully connect with refreshing token") {
            Given("MOcking returns") {
                every { googlePhotosCredentialHandler.readUserCredentialsFormFile() } returns clientCredentials
                every { googlePhotosTokenHandler.getNewAccessToken(clientCredentials) } returns accessToken
                every { googlePhotosCredentialHandler.getPhotosLibrarySettings(clientCredentials, accessToken) } returns photosLibrarySettings
                every { PhotosLibraryClient.initialize(any()) } answers { PhotosLibraryClient.create() as PhotosLibraryClient? }
            }
            Given("Access token is expired") {
                every {googlePhotosTokenHandler
                        .isRefreshingTokenRequired(accessToken)} returns true
            }

            When("Connecting") {
                googlePhotosConnectionService.initApiConnection()
            }

            Then("Connection data should be reset") {
                verify(exactly = 1) { googlePhotosTokenHandler.isRefreshingTokenRequired(accessToken) }
                verify(exactly = 2) {
                    googlePhotosTokenHandler.getNewAccessToken(clientCredentials)
                    googlePhotosCredentialHandler.getPhotosLibrarySettings(clientCredentials, accessToken)
                }

            }
            And("Client credentials should be red from file") {
                verify(exactly = 1) { googlePhotosCredentialHandler.readUserCredentialsFormFile() }
            }
            And("Connections should be initialized") {
                verify { PhotosLibraryClient.initialize(photosLibrarySettings) }
            }
            And("No more interaction with token and credentials handlers") {
                confirmVerified(googlePhotosTokenHandler, googlePhotosCredentialHandler)
            }
        }
    }
})
