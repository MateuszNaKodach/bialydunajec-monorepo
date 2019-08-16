package org.bialydunajec.gallery.infrastructure.extensions

import com.google.photos.types.proto.Album
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto

internal fun Album.toCampGalleryAlbumDto() =
        CampGalleryAlbumDto(id, title, productUrl, coverPhotoBaseUrl, mediaItemsCount)
