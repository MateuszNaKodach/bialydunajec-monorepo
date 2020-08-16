package org.bialydunajec.gallery.application

import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotosFirstPageDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto

interface CampGalleryProvider {

    fun getAlbumListByCampEdition(campEdition: String): List<CampGalleryAlbumDto>
    fun getAlbumList(): List<CampGalleryAlbumDto>
    fun getRemainingPhotosInAlbum(albumId: String): List<CampGalleryPhotoDto>
    fun getFirstPagePhotosInAlbum(albumId: String): CampGalleryPhotosFirstPageDto
    fun createAlbum(albumName: String): CampGalleryAlbumDto
}
