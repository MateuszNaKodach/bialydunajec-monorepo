package org.bialydunajec.gallery.infrastructure

import org.bialydunajec.gallery.domain.CampEditionGalleryRepository
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosCredentialService

internal class GooglePhotosCampEditionGalleryRepository(
        private val googlePhotosCredentialService: GooglePhotosCredentialService
) : CampEditionGalleryRepository {
}
