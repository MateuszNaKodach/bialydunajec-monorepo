package org.bialydunajec.gallery.infrastructure

import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.proto.NewMediaItem
import com.google.photos.library.v1.upload.UploadMediaItemResponse
import org.bialydunajec.gallery.application.CampGalleryProvider
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto.Companion.getCampEditionAlbumRegex
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto.Companion.mapTitleToDisplayVersion
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto
import org.bialydunajec.gallery.infrastructure.extensions.toCampGalleryAlbumDto
import org.bialydunajec.gallery.infrastructure.extensions.toCampGalleryPhotoDto
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService.Companion.buildUploadRequest
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService.Companion.examineBatchCreateMediaItemResponse
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosCredentialService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled

const val GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE = "org.bialydunajec.gallery.GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE"
const val GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE = "org.bialydunajec.gallery.GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE"

open class GooglePhotosGalleryProvider(private val refreshToken: String,
                                       private val credentialsJsonPath: String)
    : CampGalleryProvider{

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Cacheable(cacheNames = [GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE], key = "{#root.methodName}")
    override fun getAlbumListByCampEdition(campEdition: String): List<CampGalleryAlbumDto> =
            getAlbumList()
                    .filter { album ->
                        getCampEditionAlbumRegex(campEdition).matches(album.title) }
                    .map { album -> mapTitleToDisplayVersion(album) }

    @Cacheable(cacheNames = [GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE], key = "{#root.methodName}")
    override fun getAlbumList(): List<CampGalleryAlbumDto> =
         GooglePhotosCredentialService.execute {
            log.info("Downloading google photos albums...")
            listAlbums()
                    .iterateAll()
                    .filter { album -> getCampEditionAlbumRegex().matches(album.title) }
                    .map { album -> album.toCampGalleryAlbumDto()
                            .also { albumDto -> log.info(albumDto.title) }
                    }
         }

    @CacheEvict(cacheNames = [GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE, GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE],
            allEntries = true)
    @Scheduled(cron = "0 0 3 * * *")
    open fun cacheEvict(){
        log.info("Google photos cache evicted!")
        getAlbumList()
    }

    override fun getPhotosInAlbum(albumId: String): List<CampGalleryPhotoDto> =
            GooglePhotosCredentialService.execute {
                searchMediaItems(albumId)
                        .iterateAll()
                        .filter { mediaItem -> getMediaItemPhotoRegex().matches(mediaItem.mimeType) }
                        .map { mediaItem -> mediaItem.toCampGalleryPhotoDto()
                                .also { photo -> log.info(photo.id) }
                        }
            }

    override fun createAlbum(albumName: String): CampGalleryAlbumDto =
            GooglePhotosCredentialService.execute {
                val createdAlbum = createAlbum(albumName)
                log.info("Created album with id: ${createdAlbum.id}")
                createdAlbum.toCampGalleryAlbumDto()
            }


    private fun <T> GooglePhotosCredentialService.Companion.execute(block: PhotosLibraryClient.() -> T) =
            initApiConnection(refreshToken, credentialsJsonPath).use {
                block(it)
            }

    // TODO: add endpoint
    private fun uploadRawBytesToGoogleServer(mediaFileName: String, pathToFile: String): UploadMediaItemResponse =
            GooglePhotosCredentialService.execute {
                val uploadRequest = buildUploadRequest(mediaFileName, pathToFile)
                uploadMediaItem(uploadRequest)
            }

    // TODO: add endpoint
    private fun createMediaItemsInAlbum(albumId: String, newItems: MutableList<NewMediaItem>) =
            GooglePhotosCredentialService.execute {
                val response = batchCreateMediaItems(albumId, newItems)
                examineBatchCreateMediaItemResponse(response)
            }

    private fun getMediaItemPhotoRegex(): Regex {
        return "image/.+".toRegex()
    }

    //TODO: for testing - to remove
    /*fun testAddingPhotosFlow() {
        val album = createAlbum("albumName")
        val newMediaItemList = mutableListOf<NewMediaItem>()
        val uploadMediaBytesResponse
                = uploadRawBytesToGoogleServer("", "")
        val uploadToken = getUploadMediaItemTokenIfNoError(uploadMediaBytesResponse)
        newMediaItemList.add(NewMediaItemFactory.createNewMediaItem(uploadToken, "itemDescription"))
        createMediaItemsInAlbum(album.id, newMediaItemList)
    }*/
}
