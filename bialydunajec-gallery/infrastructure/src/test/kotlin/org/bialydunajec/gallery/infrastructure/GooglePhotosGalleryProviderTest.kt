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
        mockkObject(GooglePhotosCredentialService)
        val googlePhotosGalleryProvider
                by memoized { GooglePhotosGalleryProvider(refreshToken, credentialsJsonPath) }
        val photosLibraryClient by memoized { mockk<PhotosLibraryClient>( relaxed = true ) }
        val albumId = "albumId"

        Scenario("Successfully fetching photos") {
            val photoId = "photoId"
            val videoId = "videoId"
            val searchMediaItemsPagedResponse = mockk<SearchMediaItemsPagedResponse>( relaxed = true )

            Given("Mock initialize connection"){
                every{ GooglePhotosCredentialService.initApiConnection(any(),any()) } returns photosLibraryClient
            }
            And ("Api returns a photo and video from folder") {
                val photo = MediaItem.newBuilder().setMimeType("image/jpeg").setId(photoId).build()
                val video = MediaItem.newBuilder().setMimeType("video/mov").setId(videoId).build()
                every { photosLibraryClient.searchMediaItems(any<String>()) } returns searchMediaItemsPagedResponse
                every { searchMediaItemsPagedResponse.iterateAll() } returns
                        listOf(video, photo)
            }

            lateinit var result: List<CampGalleryPhotoDto>
            When("Download photos stored in album") {
                result = googlePhotosGalleryProvider.getPhotosInAlbum(albumId)
            }

            Then("Should invoke client method with albumId parameter and close connection") {
                verify { GooglePhotosCredentialService.initApiConnection(any(), any())}
                verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
                verify(exactly = 1) { photosLibraryClient.close() }
                verify { searchMediaItemsPagedResponse.iterateAll() }
                confirmVerified(photosLibraryClient, searchMediaItemsPagedResponse, GooglePhotosCredentialService)
            }
            And("Result should has one photo") {
                assertThat(result.size).isEqualTo(1)
                assertThat(result[0].id).isEqualTo(photoId)
            }
        }

        Scenario("Fetching photos stored in album went wrong") {
            Given("Mock initialize connection") {
                every { GooglePhotosCredentialService.initApiConnection(any(), any()) } returns photosLibraryClient
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
                verify { GooglePhotosCredentialService.initApiConnection(any(), any())}
                verify(exactly = 1) { photosLibraryClient.searchMediaItems(albumId) }
                verify(exactly = 1) { photosLibraryClient.close() }
                confirmVerified(photosLibraryClient, GooglePhotosCredentialService)
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
        mockkObject(GooglePhotosCredentialService)
        val googlePhotosGalleryProvider
                by memoized { GooglePhotosGalleryProvider(refreshToken, credentialsJsonPath) }
        mockkObject(CampGalleryAlbumDto)
        val photosLibraryClient by memoized { mockk<PhotosLibraryClient>( relaxed = true ) }

        Scenario("Successfully fetching of all albums belonging to webApp") {
            val publishedAlbumId = "publishedId"
            val hiddenAlbumId = "hiddenId"
            val webAppPrefix = "WebApp_Edycja36_"
            val albumName = "Msze Święte"
            val listAlbumsPagedResponse = mockk<ListAlbumsPagedResponse>( relaxed = true)

            Given("Mock initialize connection"){
                every { GooglePhotosCredentialService.initApiConnection(any(), any()) } returns photosLibraryClient
            }
            And("Api returns hidden and published album") {
                every { photosLibraryClient.listAlbums() } returns listAlbumsPagedResponse
                val hiddenAlbum36edition
                        = Album.newBuilder().setTitle(albumName).setId(hiddenAlbumId).build()
                val publishedAlbum36edition
                        = Album.newBuilder().setTitle(webAppPrefix + albumName).setId(publishedAlbumId).build()
                every { listAlbumsPagedResponse.iterateAll() } returns
                        listOf(hiddenAlbum36edition, publishedAlbum36edition)
            }

            lateinit var result: List<CampGalleryAlbumDto>
            When("Download information of albums") {
                result = googlePhotosGalleryProvider.getAlbumList()
            }

            Then("Should invoke client method and close connection") {
                verify { GooglePhotosCredentialService.initApiConnection(any(), any())}
                verify(exactly = 1) { photosLibraryClient.listAlbums() }
                verify(exactly = 1) { photosLibraryClient.close() }
                verify { listAlbumsPagedResponse.iterateAll() }
                confirmVerified(photosLibraryClient, listAlbumsPagedResponse, GooglePhotosCredentialService)
            }
            And("Result should has published album") {
                assertThat(result.size).isEqualTo(1)
                assertThat(result[0].id).isEqualTo(publishedAlbumId)
            }
            And("Album name should not be mapped") {
                assertThat(result[0].title).isEqualTo(webAppPrefix + albumName)
            }
        }

        Scenario("Successfully fetching of all albums belonging to webApp by camp edition") {
            val album36EdId = "album36EdId"
            val album10EdId = "album10EdId"
            val webAppPrefix36Ed = "WebApp_Edycja36_"
            val webAppPrefix10Ed = "WebApp_Edycja10_"
            val album36EdName = "Msze Święte"
            val album10EdName = "Capture the Flag!"
            val edition = "36"
            val listAlbumsPagedResponse = mockk<ListAlbumsPagedResponse>( relaxed = true)

            Given("Mock initialize connection"){
                every { GooglePhotosCredentialService.initApiConnection(any(), any()) } returns photosLibraryClient
            }
            And("Api returns 2 published albums of different editions") {
                every { photosLibraryClient.listAlbums() } returns listAlbumsPagedResponse
                val album36edition
                        = Album.newBuilder().setTitle(webAppPrefix36Ed + album36EdName).setId(album36EdId).build()
                val album10edition
                        = Album.newBuilder().setTitle(webAppPrefix10Ed + album10EdName).setId(album10EdId).build()
                every { listAlbumsPagedResponse.iterateAll() } returns listOf(album36edition, album10edition)
            }

            lateinit var result: List<CampGalleryAlbumDto>
            When("Download information of albums") {
                result = googlePhotosGalleryProvider.getAlbumListByCampEdition(edition)
            }

            Then("Should invoke client method and close connection") {
                verify { GooglePhotosCredentialService.initApiConnection(any(), any())}
                verify(exactly = 1) { photosLibraryClient.listAlbums() }
                verify(exactly = 1) { photosLibraryClient.close() }
                verify { listAlbumsPagedResponse.iterateAll() }
                confirmVerified(photosLibraryClient, listAlbumsPagedResponse, GooglePhotosCredentialService)
            }
            And("Result should has album from 36 edition") {
                assertThat(result.size).isEqualTo(1)
                assertThat(result[0].id).isEqualTo(album36EdId)
            }
            And("Album name should be mapped") {
                verify(exactly = 1) { CampGalleryAlbumDto.mapTitleToDisplayVersion(any()) }
            }
        }
    }
})
