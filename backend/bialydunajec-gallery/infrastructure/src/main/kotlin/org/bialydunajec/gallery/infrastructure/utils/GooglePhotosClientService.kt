package org.bialydunajec.gallery.infrastructure.utils

import com.google.photos.library.v1.proto.BatchCreateMediaItemsResponse
import com.google.photos.library.v1.proto.Filters
import com.google.photos.library.v1.upload.UploadMediaItemRequest
import com.google.photos.library.v1.upload.UploadMediaItemResponse
import com.google.rpc.Code
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.RandomAccessFile
import com.google.photos.library.v1.proto.MediaTypeFilter
import com.google.photos.library.v1.proto.SearchMediaItemsRequest


class GooglePhotosClientService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
        lateinit var nextPhotosPageToken: String

        fun buildSearchMediaItemsRequest(albumId: String, isFirstPage: Boolean): SearchMediaItemsRequest =
            if (isFirstPage) {
                SearchMediaItemsRequest.newBuilder()
                        .setAlbumId(albumId)
                        .setPageSize(9)
                        .build()
            } else {
                SearchMediaItemsRequest.newBuilder()
                        .setAlbumId(albumId)
                        .setPageSize(9)
                        .setPageToken(nextPhotosPageToken)
                        .build()
            }

        /**
         * Create a new upload request
         * Specify the filename that will be shown to the user in Google Photos
         * and the path to the file that will be uploaded
         *
         * @param mediaFileName filename of the media item along with the file extension
         */
        fun buildUploadRequest(mediaFileName: String, pathToFile: String): UploadMediaItemRequest =
                UploadMediaItemRequest.newBuilder()
                        .setFileName(mediaFileName)
                        .setDataFile(RandomAccessFile(pathToFile, "r"))
                        .build()


        /**
         * If the upload results in an error, handle it
         * else get the uploadToken and use this upload token to create a media item
         */
        fun getUploadMediaItemTokenIfNoError(uploadResponse: UploadMediaItemResponse): String =
                if (uploadResponse.error.isPresent()) {
                    val error = uploadResponse.error.get()
                    log.error(error.toString())
                    String()
                } else {
                    uploadResponse.uploadToken.get()
                }

        fun examineBatchCreateMediaItemResponse(response: BatchCreateMediaItemsResponse) {
            for (itemsResponse in response.newMediaItemResultsList) {
                val status = itemsResponse.status
                if (status.code == Code.OK_VALUE) {
                    val createdItem = itemsResponse.mediaItem
                    log.info("The item ${createdItem.filename} is successfully created in the user's library")
                } else {
                    log.error("The item could not be created. Status code: ${status.code}")
                }
            }
        }

        fun buildPhotoFilter(mediaTypes: List<MediaTypeFilter.MediaType>): Filters {
            val mediaTypeFilter = MediaTypeFilter.newBuilder()
                    .addAllMediaTypes(mediaTypes)
                    .build()
            return Filters.newBuilder()
                    .setMediaTypeFilter(mediaTypeFilter)
                    .build()
        }
    }
}
