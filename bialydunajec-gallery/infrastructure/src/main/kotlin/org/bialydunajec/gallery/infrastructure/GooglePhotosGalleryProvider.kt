package org.bialydunajec.gallery.infrastructure

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.PhotosLibrarySettings
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.*
import com.google.photos.types.proto.Album
import org.bialydunajec.gallery.application.CampGalleryProvider
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory


open class GooglePhotosGalleryProvider : CampGalleryProvider{

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    private val googlePhotosClient: GooglePhotosClient = GooglePhotosClient()

    private val settings: PhotosLibrarySettings = PhotosLibrarySettings
            .newBuilder()
            .setCredentialsProvider(
                    FixedCredentialsProvider.create(googlePhotosClient.credentials)
            ).build()

    private val photosLibraryClient: PhotosLibraryClient  = PhotosLibraryClient.initialize(settings)


    override fun getAlbumList(): List<CampGalleryAlbumDto> {
        var campGalleryAlbumList: List<CampGalleryAlbumDto> = emptyList()
        try {
            val response: ListAlbumsPagedResponse = photosLibraryClient.listAlbums()
            for (album in response.iterateAll())
                log.info(album.title)
            campGalleryAlbumList = response.iterateAll().map {
                CampGalleryAlbumDto(it.id, it.title, it.productUrl, it.coverPhotoBaseUrl, it.mediaItemsCount)
            }
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
          //  photosLibraryClient.close()
        }

        return campGalleryAlbumList
    }


    override fun createAlbum(albumName: String) {
        try {
            val createdAlbum: Album = photosLibraryClient.createAlbum(albumName)
            log.info("Created album with id: ${createdAlbum.id}")
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
       //     photosLibraryClient.close()
        }
    }









}
