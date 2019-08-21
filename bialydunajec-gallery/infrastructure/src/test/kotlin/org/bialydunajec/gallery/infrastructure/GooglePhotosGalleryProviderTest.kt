package org.bialydunajec.gallery.infrastructure

import com.google.api.gax.grpc.GrpcStatusCode
import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.ListAlbumsPagedResponse
import com.google.photos.types.proto.Album
import io.grpc.Status
import io.mockk.*
import org.assertj.core.api.Assertions.*
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
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
        val googlePhotosGalleryProvider by memoized {GooglePhotosGalleryProvider() }
        val photosLibraryClient by memoized { mockk<PhotosLibraryClient>( relaxed = true ) }
        val albumId = "albumId"

        Scenario("Successfully fetching") {
            Given("Mock initialize connection"){
                mockkObject(GooglePhotosCredentialService)
                every { GooglePhotosCredentialService.initApiConnection()  } returns photosLibraryClient
            }

            When("Download photos stored in album") {
                googlePhotosGalleryProvider.getPhotosInAlbum(albumId)
            }

            Then("Should invoke client method with albumId parameter and close connection") {
                verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
                verify(exactly = 1) { photosLibraryClient.close() }
                confirmVerified(photosLibraryClient)
            }
        }

        Scenario("Fetching photos stored in album went wrong") {
            Given("Mock initialize connection") {
                mockkObject(GooglePhotosCredentialService)
                every { GooglePhotosCredentialService.initApiConnection()  } returns photosLibraryClient
            }

            And("Search media items will throw exception") {
                val throwable = Throwable("Throwable message")
                val statusCode = GrpcStatusCode.of(Status.Code.UNAUTHENTICATED)
                val apiException = ApiException("message", throwable, statusCode, false)
                every { photosLibraryClient.searchMediaItems(ArgumentMatchers.anyString()) } throws apiException
            }

            When("Download photos stored in album") {
                googlePhotosGalleryProvider.getPhotosInAlbum(albumId)
            }

            Then("Should invoke client method with albumId parameter and close connection") {
                verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
                verify(exactly = 1) { photosLibraryClient.close() }
                confirmVerified(photosLibraryClient)
            }
            And("Exception is thrown") {
                thrown.expect(ApiException::class.java)
            }
        }
    }

    Feature("Fetching information of albums in google gallery"){
        val googlePhotosGalleryProvider by memoized { GooglePhotosGalleryProvider() }
        val photosLibraryClient by memoized { mockk<PhotosLibraryClient>( relaxed = true ) }

        Scenario("Successfully fetching") {
            val listAlbumsPagedResponse = mockk<ListAlbumsPagedResponse>( relaxed = true)

            Given("Mock initialize connection"){
                mockkObject(GooglePhotosCredentialService)
                every { GooglePhotosCredentialService.initApiConnection()  } returns photosLibraryClient
            }
            And("Api will return album in list") {
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
                confirmVerified(photosLibraryClient)
                verify { listAlbumsPagedResponse.iterateAll() }
                confirmVerified(listAlbumsPagedResponse)
            }
            And("result not empty") {
                assertThat(result).isNotEmpty()
            }
        }
    }
})
