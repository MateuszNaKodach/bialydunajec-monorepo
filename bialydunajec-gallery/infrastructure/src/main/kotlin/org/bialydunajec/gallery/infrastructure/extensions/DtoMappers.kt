package org.bialydunajec.gallery.infrastructure.extensions

import com.google.photos.types.proto.Album
import com.google.photos.types.proto.MediaItem
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto

internal fun Album.toCampGalleryAlbumDto() =
        CampGalleryAlbumDto(id, title, productUrl, coverPhotoBaseUrl, mediaItemsCount)

internal fun MediaItem.toCampGalleryPhotoDto() =
        CampGalleryPhotoDto(id, baseUrl, description)
