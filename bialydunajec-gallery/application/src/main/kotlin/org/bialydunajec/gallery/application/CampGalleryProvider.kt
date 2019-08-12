package org.bialydunajec.gallery.application

import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto

interface CampGalleryProvider {

    fun getAlbumList(): List<CampGalleryAlbumDto>
    fun createAlbum(albumName: String)
}
