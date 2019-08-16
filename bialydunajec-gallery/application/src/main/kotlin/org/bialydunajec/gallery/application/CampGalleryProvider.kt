package org.bialydunajec.gallery.application

import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto

interface CampGalleryProvider {

    fun getAlbumList(): List<CampGalleryAlbumDto>
    fun getAlbumListByCampEdition(campEditionId: String): CampGalleryAlbumDto
    fun getPhotosInAlbum(albumId: String): List<String>
    fun createAlbum(albumName: String)
}
