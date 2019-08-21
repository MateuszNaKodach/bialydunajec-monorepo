package org.bialydunajec.gallery.infrastructure

import assertk.assertThat
import com.google.photos.library.v1.PhotosLibraryClient
import io.mockk.*
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosCredentialService
import org.mockito.ArgumentMatchers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object GooglePhotosGalleryProviderTest: Spek( {

    Feature("Fetching media from google photos gallery") {

        Scenario("Fetching photos stored in album") {
            val albumId = "albumId"
            lateinit var googlePhotosGalleryProvider: GooglePhotosGalleryProvider
            lateinit var photosLibraryClient: PhotosLibraryClient

            Given("Initialize connection"){
                photosLibraryClient = mockk<PhotosLibraryClient>(relaxed = true)
                mockkObject(GooglePhotosCredentialService)
                every { GooglePhotosCredentialService.initApiConnection()  } returns photosLibraryClient
                googlePhotosGalleryProvider = GooglePhotosGalleryProvider()
            }

            lateinit var result: List<String>
            When("Download photos stored in album") {
                googlePhotosGalleryProvider.getPhotosInAlbum(albumId)
            }

            Then("Should invoke client method with albumId parameter and close connection") {
                verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
                verify(exactly = 1) { photosLibraryClient.close() }
                confirmVerified(photosLibraryClient)
                assertThat{ result.isNotEmpty() }
            }
        }

        Scenario("Fetching photos stored in album went wrong") {
            val albumId = "albumId"
            lateinit var googlePhotosGalleryProvider: GooglePhotosGalleryProvider
            lateinit var photosLibraryClient: PhotosLibraryClient

            Given("Initialize connection"){
                photosLibraryClient = mockk<PhotosLibraryClient>(relaxed = true)
                every { photosLibraryClient.searchMediaItems(ArgumentMatchers.anyString()) } throws IndexOutOfBoundsException()
                mockkObject(GooglePhotosCredentialService)
                every { GooglePhotosCredentialService.initApiConnection()  } returns photosLibraryClient
                googlePhotosGalleryProvider = GooglePhotosGalleryProvider()
            }

            lateinit var result: List<String>
            When("Download photos stored in album") {
                result = googlePhotosGalleryProvider.getPhotosInAlbum(albumId)
            }

            Then("Should invoke client method with albumId parameter and close connection") {
                verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
                verify(exactly = 1) { photosLibraryClient.close() }
                confirmVerified(photosLibraryClient)
                assertThat{ result.isEmpty() }
            }
        }



        Scenario("Fetching information of albums in google gallery") {
            lateinit var googlePhotosGalleryProvider: GooglePhotosGalleryProvider
            lateinit var photosLibraryClient: PhotosLibraryClient

            Given("Initialize connection"){
                photosLibraryClient = mockk<PhotosLibraryClient>(relaxed = true)
                mockkObject(GooglePhotosCredentialService)
                every { GooglePhotosCredentialService.initApiConnection()  } returns photosLibraryClient
                googlePhotosGalleryProvider = GooglePhotosGalleryProvider()
            }

            When("Download information of albums") {
                googlePhotosGalleryProvider.getAlbumList()
            }

            Then("Should invoke client method and close connection") {
                verify(exactly = 1) { photosLibraryClient.listAlbums() }
                verify(exactly = 1) { photosLibraryClient.close() }
                confirmVerified(photosLibraryClient)
            }
        }

    }
})
