package org.bialydunajec.gallery.infrastructure.extensions

import com.google.photos.library.v1.proto.NewMediaItem
import com.google.photos.library.v1.util.NewMediaItemFactory

internal fun MutableList<NewMediaItem>.addMediaItem(uploadToken: String, itemDescription: String) {
    // Create a NewMediaItem with the uploadToken obtained from the previous upload request, and a description
    val newMediaItem = NewMediaItemFactory
            .createNewMediaItem(uploadToken, itemDescription)
    this.add(newMediaItem)
}
