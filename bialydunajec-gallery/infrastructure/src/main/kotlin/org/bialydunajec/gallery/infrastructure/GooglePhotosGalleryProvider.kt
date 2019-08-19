package org.bialydunajec.gallery.infrastructure

import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.types.proto.Album
import org.bialydunajec.gallery.application.CampGalleryProvider
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.infrastructure.extensions.toCampGalleryAlbumDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import kotlin.streams.toList

const val GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE = "org.bialydunajec.gallery.GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE"
const val GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE = "org.bialydunajec.gallery.GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE"

open class GooglePhotosGalleryProvider : CampGalleryProvider{

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Cacheable(cacheNames = [GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE], key = "{#root.methodName}")
    override fun getAlbumListByCampEdition(campEditionId: String): List<CampGalleryAlbumDto> {
        val campEditionAlbums = getAlbumList().stream()
                .filter { album -> album.title!!.startsWith(campEditionId) } // TODO: consistent convention
                .toList()
        return campEditionAlbums
    }

    override fun getPhotosInAlbum(albumId: String): List<String> {
        val photosLibraryClient: PhotosLibraryClient = GooglePhotosCredentialService.initApiConnection()
        var photosBaseUrlList: List<String> = emptyList()

        try {
            val response = photosLibraryClient.searchMediaItems(albumId)
            photosBaseUrlList = response.iterateAll()
                    .map { it.baseUrl
                            .also { photo -> log.info(photo) } }
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
            photosLibraryClient.close()
        }

        return photosBaseUrlList
    }

    @Cacheable(cacheNames = [GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE], key = "{#root.methodName}")
    override fun getAlbumList(): List<CampGalleryAlbumDto> {
        val photosLibraryClient: PhotosLibraryClient = GooglePhotosCredentialService.initApiConnection()
        var campGalleryAlbumList: List<CampGalleryAlbumDto> = emptyList()

        try {
            log.info("Downloading google photos albums...")
            campGalleryAlbumList = photosLibraryClient.listAlbums()
                    .iterateAll()
                    .map { it.toCampGalleryAlbumDto()
                            .also { album -> log.info(album.title) } }
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
            photosLibraryClient.close()
        }

        return campGalleryAlbumList
    }

    @CacheEvict(cacheNames = [GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE, GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE],
            allEntries = true)
    @Scheduled(cron = "0 0 3 * * *")
    open fun cacheEvict(){
        log.info("Google photos cache evicted!")
    }

    override fun createAlbum(albumName: String) {
        val photosLibraryClient: PhotosLibraryClient = GooglePhotosCredentialService.initApiConnection()

        try {
            val createdAlbum: Album = photosLibraryClient.createAlbum(albumName)
            log.info("Created album with id: ${createdAlbum.id}")
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
            photosLibraryClient.close()
        }
    }
}
