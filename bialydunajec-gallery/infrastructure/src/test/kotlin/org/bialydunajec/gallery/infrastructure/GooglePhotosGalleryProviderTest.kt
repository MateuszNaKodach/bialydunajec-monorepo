package org.bialydunajec.gallery.infrastructure

import com.google.api.gax.grpc.GrpcStatusCode
import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.ListAlbumsPagedResponse
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.SearchMediaItemsPagedResponse
import com.google.photos.types.proto.Album
import com.google.photos.types.proto.MediaItem
import io.grpc.Status
import io.mockk.*
import org.assertj.core.api.Assertions.*
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosCredentialService
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.mockito.ArgumentMatchers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@Rule
var thrown: ExpectedException = ExpectedException.none()

object GooglePhotosGalleryProviderTest: Spek( {

    Feature("Fetching photos stored in album") {
        val refreshToken = "rT"
        val credentialsJsonPath = "cJP"
        val googlePhotosGalleryProvider
                by memoized { GooglePhotosGalleryProvider(refreshToken, credentialsJsonPath) }
        val photosLibraryClient by memoized { mockk<PhotosLibraryClient>( relaxed = true ) }
        val albumId = "albumId"

        Scenario("Successfully fetching") {
            val searchMediaItemsPagedResponse = mockk<SearchMediaItemsPagedResponse>( relaxed = true )

            Given("Mock initialize connection"){
                mockkObject(GooglePhotosCredentialService)
                every{
                    GooglePhotosCredentialService.initApiConnection(any())
                } returns photosLibraryClient
            }
            And ("Api returns a photo") {
                every { photosLibraryClient.searchMediaItems(any<String>()) } returns searchMediaItemsPagedResponse
                every { searchMediaItemsPagedResponse.iterateAll() } returns
                        listOf(MediaItem.getDefaultInstance())
            }

            lateinit var result: List<CampGalleryPhotoDto>
            When("Download photos stored in album") {
                result = googlePhotosGalleryProvider.getPhotosInAlbum(albumId)
            }

            Then("Should invoke client method with albumId parameter and close connection") {
                verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
                verify(exactly = 1) { photosLibraryClient.close() }
                verify { searchMediaItemsPagedResponse.iterateAll() }
                confirmVerified(photosLibraryClient, searchMediaItemsPagedResponse)
            }
            And("Result should has photos") {
                assertThat(result.size).isEqualTo(1)
            }
        }

        Scenario("Fetching photos stored in album went wrong") {
            Given("Mock initialize connection") {
                mockkObject(GooglePhotosCredentialService)
                every {
                    GooglePhotosCredentialService.initApiConnection(any())
                } returns photosLibraryClient
            }
            And("Search media items will throw exception") {
                val throwable = Throwable("Throwable message")
                val statusCode = GrpcStatusCode.of(Status.Code.UNAUTHENTICATED)
                val apiException = ApiException("message", throwable, statusCode, false)
                every { photosLibraryClient.searchMediaItems(ArgumentMatchers.anyString()) } throws apiException
            }

            lateinit var result: List<CampGalleryPhotoDto>
            When("Try download photos stored in album") {
                result = googlePhotosGalleryProvider.getPhotosInAlbum(albumId)
            }

            Then("Should invoke client method with albumId parameter and close connection") {
                verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
                verify(exactly = 1) { photosLibraryClient.close() }
                confirmVerified(photosLibraryClient)
            }
            And("Exception should be thrown and result be empty") {
                thrown.expect(ApiException::class.java)
                assertThat(result).isEmpty()
            }
        }
    }

    Feature("Fetching information of albums in google gallery"){
        val refreshToken = "rT"
        val credentialsJsonPath = "cJP"
        val googlePhotosGalleryProvider
                by memoized { GooglePhotosGalleryProvider(refreshToken, credentialsJsonPath) }
        val photosLibraryClient by memoized { mockk<PhotosLibraryClient>( relaxed = true ) }

        Scenario("Successfully fetching") {
            val listAlbumsPagedResponse = mockk<ListAlbumsPagedResponse>( relaxed = true)

            Given("Mock initialize connection"){
                mockkObject(GooglePhotosCredentialService)
                every { GooglePhotosCredentialService.initApiConnection(any()) } returns photosLibraryClient
            }
            And("Api returns album in list") {
                every { photosLibraryClient.listAlbums() } returns listAlbumsPagedResponse
                val album = Album.getDefaultInstance()
                every { listAlbumsPagedResponse.iterateAll() } returns listOf(album)
            }

            lateinit var result: List<CampGalleryAlbumDto>
            When("Download information of albums") {
                result = googlePhotosGalleryProvider.getAlbumList()
            }

            Then("Should invoke client method and close connection") {
                verify(exactly = 1) { photosLibraryClient.listAlbums() }
                verify(exactly = 1) { photosLibraryClient.close() }
                verify { listAlbumsPagedResponse.iterateAll() }
                confirmVerified(photosLibraryClient, listAlbumsPagedResponse)
            }
            And("Result should has album") {
                assertThat(result.size).isEqualTo(1)
            }
        }
    }
})
