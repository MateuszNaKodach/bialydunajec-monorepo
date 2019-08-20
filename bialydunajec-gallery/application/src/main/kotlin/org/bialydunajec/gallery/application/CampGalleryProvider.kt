package org.bialydunajec.gallery.application

import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto

interface CampGalleryProvider {

    fun testAddingPhotosFlow()
    fun getAlbumListByCampEdition(campEditionId: String): List<CampGalleryAlbumDto>
    fun getPhotosInAlbum(albumId: String): List<String>
    fun createAlbum(albumName: String): CampGalleryAlbumDto
}
