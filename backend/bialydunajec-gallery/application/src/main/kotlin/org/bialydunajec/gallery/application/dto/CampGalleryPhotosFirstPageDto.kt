package org.bialydunajec.gallery.application.dto

data class CampGalleryPhotosFirstPageDto (
    val campGalleryPhotoDtos: List<CampGalleryPhotoDto>,
    val areRemainingPhotos: Boolean
    )

