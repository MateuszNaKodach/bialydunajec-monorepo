package org.bialydunajec.gallery.application

import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto

interface CampGalleryProvider {

    fun testAddingPhotosFlow()
    fun getAlbumListByCampEdition(campEditionId: String): List<CampGalleryAlbumDto>
    fun getPhotosInAlbum(albumId: String): List<CampGalleryPhotoDto>
    fun createAlbum(albumName: String): CampGalleryAlbumDto
}
