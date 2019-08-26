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



class GooglePhotosClientService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)

        // Create a new upload request
        // Specify the filename that will be shown to the user in Google Photos
        // and the path to the file that will be uploaded
        fun buildUploadRequest(mediaFileName: String, pathToFile: String): UploadMediaItemRequest =
                UploadMediaItemRequest.newBuilder()
                        //filename of the media item along with the file extension
                        .setFileName(mediaFileName)
                        .setDataFile(RandomAccessFile(pathToFile, "r"))
                        .build()


        fun getUploadMediaItemTokenIfNoError(uploadResponse: UploadMediaItemResponse): String =
                if (uploadResponse.error.isPresent()) {
                    // If the upload results in an error, handle it
                    val error = uploadResponse.error.get()
                    log.error(error.toString())
                    String()
                } else {
                    // If the upload is successful, get the uploadToken
                    // Use this upload token to create a media item
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
