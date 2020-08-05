package org.bialydunajec.gallery.infrastructure

import org.bialydunajec.gallery.application.CampGalleryProvider
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotoDto
import org.bialydunajec.gallery.application.dto.CampGalleryPhotosFirstPageDto
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@ConditionalOnProperty(name = ["bialydunajec.camp-gallery-provider"], havingValue = "empty")
@Component
class EmptyCampGalleryProvider : CampGalleryProvider {
    override fun getAlbumListByCampEdition(campEdition: String): List<CampGalleryAlbumDto> = listOf()

    override fun getAlbumList(): List<CampGalleryAlbumDto> = listOf()

    override fun getRemainingPhotosInAlbum(albumId: String): List<CampGalleryPhotoDto> = listOf()

    override fun getFirstPagePhotosInAlbum(albumId: String): CampGalleryPhotosFirstPageDto = CampGalleryPhotosFirstPageDto(listOf(), false)

    override fun createAlbum(albumName: String): CampGalleryAlbumDto {
        TODO("Not yet implemented")
    }


}