package org.bialydunajec.gallery.infrastructure

import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.ListAlbumsPagedResponse
import com.google.photos.types.proto.Album
import org.bialydunajec.gallery.application.CampGalleryProvider
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.infrastructure.extensions.toCampGalleryAlbumDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory


internal class GooglePhotosGalleryProvider : CampGalleryProvider{

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    private val photosLibraryClient: PhotosLibraryClient = GooglePhotosCredentialService.initApiConnection()


    override fun getAlbumListByCampEdition(campEditionId: String): CampGalleryAlbumDto {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPhotosInAlbum(albumId: String): List<String> {
        var photosBaseUrlList: List<String> = emptyList()

        try {
            val response = photosLibraryClient.searchMediaItems(albumId)
            for (photoItem in response.iterateAll()) {
                log.info(photoItem.baseUrl)
            }
            photosBaseUrlList = response.iterateAll().map { it.baseUrl  }
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
            // photosLibraryClient.close()
        }

        return photosBaseUrlList
    }


    override fun getAlbumList(): List<CampGalleryAlbumDto> {
        var campGalleryAlbumList: List<CampGalleryAlbumDto> = emptyList()

        try {
            val response: ListAlbumsPagedResponse = photosLibraryClient.listAlbums()
            for (album in response.iterateAll()) {
                log.info(album.title)
            }
            campGalleryAlbumList = response.iterateAll()
                    .map { it.toCampGalleryAlbumDto() }
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
            // photosLibraryClient.close()
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
            // photosLibraryClient.close()
        }
    }



}
