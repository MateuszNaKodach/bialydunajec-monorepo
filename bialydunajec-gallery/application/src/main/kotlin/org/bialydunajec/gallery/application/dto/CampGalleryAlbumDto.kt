package org.bialydunajec.gallery.application.dto

data class CampGalleryAlbumDto(
        val id: String,
        val title: String,
        val albumUrl: String,
        val coverPhotoUrl: String,
        val photosCount: Long
)
