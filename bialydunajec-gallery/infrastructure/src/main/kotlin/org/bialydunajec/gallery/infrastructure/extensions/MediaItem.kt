package org.bialydunajec.gallery.infrastructure.extensions

import com.google.photos.types.proto.MediaItem
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto

internal fun MediaItem.toCampGalleryPhotoDto() =
        CampGalleryPhotoDto(id, baseUrl, description)
