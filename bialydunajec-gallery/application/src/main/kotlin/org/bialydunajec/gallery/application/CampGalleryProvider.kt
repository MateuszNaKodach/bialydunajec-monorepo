package org.bialydunajec.gallery.application

import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto

interface CampGalleryProvider {

    fun getAlbumListByCampEdition(campEdition: String): List<CampGalleryAlbumDto>
    fun getAlbumList(): List<CampGalleryAlbumDto>
    fun getPhotosInAlbum(albumId: String): List<CampGalleryPhotoDto>
    fun createAlbum(albumName: String): CampGalleryAlbumDto
}
