package org.bialydunajec.gallery.infrastructure.utils

import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.proto.BatchCreateMediaItemsResponse
import com.google.photos.library.v1.proto.NewMediaItem
import com.google.photos.library.v1.upload.UploadMediaItemRequest
import com.google.photos.library.v1.upload.UploadMediaItemResponse
import com.google.rpc.Code
import org.bialydunajec.gallery.infrastructure.extensions.addMediaItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.RandomAccessFile

class GooglePhotosClientService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this.javaClass)

        // Create a new upload request
        // Specify the filename that will be shown to the user in Google Photos
        // and the path to the file that will be uploaded
        fun buildUploadRequest(mediaFileName: String, pathToFile: String): UploadMediaItemRequest? {
            return UploadMediaItemRequest.newBuilder()
                    //filename of the media item along with the file extension
                    .setFileName(mediaFileName)
                    .setDataFile(RandomAccessFile(pathToFile, "r"))
                    .build()
        }

        fun getUploadMediaItemTokenIfNoError(uploadResponse: UploadMediaItemResponse): String {
            return if (uploadResponse.error.isPresent()) {
                // If the upload results in an error, handle it
                val error = uploadResponse.error.get()
                log.error(error.toString())
                String()
            } else {
                // If the upload is successful, get the uploadToken
                // Use this upload token to create a media item
                uploadResponse.uploadToken.get()
            }
        }

        fun examineBatchCreateMediaItemResponse(response: BatchCreateMediaItemsResponse) {
            for (itemsResponse in response.newMediaItemResultsList) {
                val status = itemsResponse.status
                if (status.code === Code.OK_VALUE) {
                    val createdItem = itemsResponse.mediaItem
                    log.info("The item ${createdItem.filename} is successfully created in the user's library")
                } else {
                    log.error("The item could not be created. Status code: ${status.code}")
                }
            }
        }

        fun addMediaItemToList(mediaItemList: MutableList<NewMediaItem>,
                                       uploadToken: String, itemDescription: String) {
            try {
                mediaItemList.addMediaItem(uploadToken, itemDescription)
            } catch (e: ApiException) {
                log.error(e.toString())
            }
        }
    }
}
