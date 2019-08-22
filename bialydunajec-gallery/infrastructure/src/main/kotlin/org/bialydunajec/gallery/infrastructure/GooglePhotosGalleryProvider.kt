package org.bialydunajec.gallery.infrastructure

import com.google.photos.library.v1.proto.NewMediaItem
import com.google.photos.library.v1.upload.UploadMediaItemResponse
import com.google.photos.library.v1.util.NewMediaItemFactory
import org.bialydunajec.gallery.application.CampGalleryProvider
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.infrastructure.extensions.toCampGalleryAlbumDto
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService.Companion.buildUploadRequest
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService.Companion.examineBatchCreateMediaItemResponse
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService.Companion.getUploadMediaItemTokenIfNoError
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosCredentialService
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
                .filter { album -> album.title.startsWith(campEditionId) } // TODO: consistent convention
                .toList()
        return campEditionAlbums
    }

    @Cacheable(cacheNames = [GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE], key = "{#root.methodName}")
    open fun getAlbumList(): List<CampGalleryAlbumDto> {
         GooglePhotosCredentialService.initApiConnection().use {
            log.info("Downloading google photos albums...")
            return it.listAlbums()
                    .iterateAll()
                    .map { album -> album.toCampGalleryAlbumDto()
                            .also { albumDto -> log.info(albumDto.title) }
                    }
        }
    }

    @CacheEvict(cacheNames = [GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE, GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE],
            allEntries = true)
    @Scheduled(cron = "0 0 3 * * *")
    open fun cacheEvict(){
        log.info("Google photos cache evicted!")
    }

    override fun getPhotosInAlbum(albumId: String): List<String> {
        GooglePhotosCredentialService.initApiConnection().use {
            return it.searchMediaItems(albumId)
                    .iterateAll()
                    .map { mediaItem -> mediaItem.baseUrl
                            .also { photo -> log.info(photo) }
                    }
        }
    }

    override fun createAlbum(albumName: String): CampGalleryAlbumDto {
        GooglePhotosCredentialService.initApiConnection().use {
            val createdAlbum = it.createAlbum(albumName)
            log.info("Created album with id: ${createdAlbum.id}")
            return createdAlbum.toCampGalleryAlbumDto()
        }
    }

    private fun uploadRawBytesToGoogleServer(mediaFileName: String, pathToFile: String): UploadMediaItemResponse {
        GooglePhotosCredentialService.initApiConnection().use {
            val uploadRequest = buildUploadRequest(mediaFileName, pathToFile)
            return it.uploadMediaItem(uploadRequest)
        }
    }

    private fun createMediaItemsInAlbum(albumId: String, newItems: MutableList<NewMediaItem>) {
        GooglePhotosCredentialService.initApiConnection().use {
            val response = it.batchCreateMediaItems(albumId, newItems)
            examineBatchCreateMediaItemResponse(response)
        }
    }

    override fun testAddingPhotosFlow() {
        val album = createAlbum("albumName")
        val newMediaItemList = mutableListOf<NewMediaItem>()
        val uploadMediaBytesResponse
                = uploadRawBytesToGoogleServer("", "")
        val uploadToken = getUploadMediaItemTokenIfNoError(uploadMediaBytesResponse)
        newMediaItemList.add(NewMediaItemFactory.createNewMediaItem(uploadToken, "itemDescription"))
        createMediaItemsInAlbum(album.id, newMediaItemList)
    }
}
