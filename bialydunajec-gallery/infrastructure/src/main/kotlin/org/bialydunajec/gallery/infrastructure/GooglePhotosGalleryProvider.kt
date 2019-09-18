package org.bialydunajec.gallery.infrastructure

import com.google.photos.library.v1.PhotosLibraryClient
import com.google.photos.library.v1.proto.NewMediaItem
import com.google.photos.library.v1.upload.UploadMediaItemResponse
import org.bialydunajec.gallery.application.CampGalleryProvider
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto.Companion.getCampEditionAlbumRegex
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto.Companion.mapTitleToDisplayVersion
import org.bialydunajec.gallery.application.dto.CampGalleryPhotosFirstPageDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto
import org.bialydunajec.gallery.infrastructure.extensions.toCampGalleryAlbumDto
import org.bialydunajec.gallery.infrastructure.extensions.toCampGalleryPhotoDto
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService.Companion.buildSearchMediaItemsRequest
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService.Companion.buildUploadRequest
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosClientService.Companion.examineBatchCreateMediaItemResponse
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosConnectionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled

const val GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE = "org.bialydunajec.gallery.GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE"
const val GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE = "org.bialydunajec.gallery.GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE"

open class GooglePhotosGalleryProvider (
        private val googlePhotosConnectionService: GooglePhotosConnectionService
) : CampGalleryProvider{

    private val log: Logger = LoggerFactory.getLogger(GooglePhotosGalleryProvider::class.java)
    private val mediaItemPhotoRegex = "image/.+".toRegex()

    @Cacheable(cacheNames = [GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE], key = "{#root.methodName, #campEdition}")
    override fun getAlbumListByCampEdition(campEdition: String): List<CampGalleryAlbumDto> =
            getAlbumList()
                    .filter { album -> getCampEditionAlbumRegex(campEdition).matches(album.title) }
                    .map { album -> mapTitleToDisplayVersion(album) }

    @Cacheable(cacheNames = [GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE], key = "{#root.methodName}")
    override fun getAlbumList(): List<CampGalleryAlbumDto> =
         googlePhotosConnectionService.execute {
            log.info("Downloading google photos albums...")
            listAlbums()
                    .iterateAll()
                    .filter { album -> getCampEditionAlbumRegex().matches(album.title) }
                    .map { album -> album.toCampGalleryAlbumDto() }
         }

    @CacheEvict(cacheNames = [GOOGLE_PHOTOS_ALBUMS_SPRING_CACHE, GOOGLE_PHOTOS_EDITION_ALBUMS_SPRING_CACHE],
            allEntries = true)
    @Scheduled(cron = "0 0 3 * * *")
    open fun cacheEvict(){
        log.info("Google photos cache evicted!")
        getAlbumList()
    }

    override fun getFirstPagePhotosInAlbum(albumId: String): CampGalleryPhotosFirstPageDto =
        googlePhotosConnectionService.execute {
            val pagedResponse
                    = searchMediaItems(buildSearchMediaItemsRequest(albumId, isFirstPage = true))

            GooglePhotosClientService.nextPhotosPageToken = pagedResponse.nextPageToken

            CampGalleryPhotosFirstPageDto(
                    pagedResponse.expandToFixedSizeCollection(9).values
                            .filter { mediaItem -> mediaItemPhotoRegex.matches(mediaItem.mimeType) }
                            .map { mediaItem -> mediaItem.toCampGalleryPhotoDto() },
                    areRemainingPhotos = pagedResponse.nextPageToken.isNotEmpty()
            )
        }

    override fun getRemainingPhotosInAlbum(albumId: String): List<CampGalleryPhotoDto> =
            googlePhotosConnectionService.execute {
                searchMediaItems(buildSearchMediaItemsRequest(albumId, isFirstPage = false))
                        .iterateAll()
                        .filter { mediaItem -> mediaItemPhotoRegex.matches(mediaItem.mimeType) }
                        .map { mediaItem -> mediaItem.toCampGalleryPhotoDto() }
            }

    override fun createAlbum(albumName: String): CampGalleryAlbumDto =
            googlePhotosConnectionService.execute {
                val createdAlbum = createAlbum(albumName)
                log.info("Created album with id: ${createdAlbum.id}")
                createdAlbum.toCampGalleryAlbumDto()
            }

    private fun <T> GooglePhotosConnectionService.execute(block: PhotosLibraryClient.() -> T) =
            initApiConnection().use {
                block(it)
            }

    // TODO: add endpoint
    private fun uploadRawBytesToGoogleServer(mediaFileName: String, pathToFile: String): UploadMediaItemResponse =
            googlePhotosConnectionService.execute {
                val uploadRequest = buildUploadRequest(mediaFileName, pathToFile)
                uploadMediaItem(uploadRequest)
            }

    // TODO: add endpoint
    private fun createMediaItemsInAlbum(albumId: String, newItems: MutableList<NewMediaItem>) =
            googlePhotosConnectionService.execute {
                val response = batchCreateMediaItems(albumId, newItems)
                examineBatchCreateMediaItemResponse(response)
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
