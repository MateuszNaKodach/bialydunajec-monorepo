package org.bialydunajec.gallery.infrastructure

import org.bialydunajec.gallery.domain.CampEditionGalleryRepository

internal class GooglePhotosCampEditionGalleryRepository(
        private val googlePhotosCredentialService: GooglePhotosCredentialService
) : CampEditionGalleryRepository {
}
