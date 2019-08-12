package org.bialydunajec.gallery.infrastructure

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.PhotosLibrarySettings
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.*
import com.google.photos.types.proto.Album
import org.bialydunajec.gallery.application.CampGalleryProvider
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


    override fun getAlbumList() {
        try {
            val response: ListAlbumsPagedResponse = photosLibraryClient.listAlbums()
            for (album in response.iterateAll())
                log.info(album.title)
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
          //  photosLibraryClient.close()
        }
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
