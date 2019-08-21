package org.bialydunajec.gallery.infrastructure

import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.PhotosLibraryClient
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
import java.io.FileNotFoundException
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

    override fun createAlbum(albumName: String): CampGalleryAlbumDto {
        val photosLibraryClient: PhotosLibraryClient = GooglePhotosCredentialService.initApiConnection()
        lateinit var createdAlbumDto: CampGalleryAlbumDto

        try {
            val createdAlbum = photosLibraryClient.createAlbum(albumName)
            log.info("Created album with id: ${createdAlbum.id}")
            createdAlbumDto = createdAlbum.toCampGalleryAlbumDto()
        } catch (exc: ApiException) {
            log.error(exc.message)
        } finally {
            photosLibraryClient.close()
        }

        return createdAlbumDto
    }

    private fun uploadRawBytesToGoogleServer(mediaFileName: String, pathToFile: String): UploadMediaItemResponse {
        val photosLibraryClient: PhotosLibraryClient = GooglePhotosCredentialService.initApiConnection()
        lateinit var uploadResponse: UploadMediaItemResponse

        try {
            val uploadRequest = buildUploadRequest(mediaFileName, pathToFile)
            uploadResponse = photosLibraryClient.uploadMediaItem(uploadRequest)
        } catch (e: ApiException) {
            log.error(e.toString())
        } catch (e: FileNotFoundException) {
            log.error(e.toString())
        } finally {
            photosLibraryClient.close()
        }

        return uploadResponse
    }

    private fun createMediaItemsInAlbum(albumId: String, newItems: MutableList<NewMediaItem>) {
        val photosLibraryClient: PhotosLibraryClient = GooglePhotosCredentialService.initApiConnection()

        try {
            val response = photosLibraryClient.batchCreateMediaItems(albumId, newItems)
            examineBatchCreateMediaItemResponse(response)
        } catch (e: ApiException) {
            log.error(e.toString())
        } finally {
            photosLibraryClient.close()
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
