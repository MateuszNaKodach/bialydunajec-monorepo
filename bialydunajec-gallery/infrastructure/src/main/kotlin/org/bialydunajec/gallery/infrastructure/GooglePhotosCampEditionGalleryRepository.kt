package org.bialydunajec.gallery.infrastructure

import org.bialydunajec.gallery.domain.CampEditionGalleryRepository
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosConnectionService

internal class GooglePhotosCampEditionGalleryRepository(
        private val googlePhotosConnectionService: GooglePhotosConnectionService
) : CampEditionGalleryRepository {
}
