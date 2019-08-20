package org.bialydunajec.gallery.presentation.rest.v1.admin

import org.bialydunajec.gallery.application.CampGalleryProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/admin/camp-gallery")
internal class CampGalleryAdminController(private val campGalleryProvider: CampGalleryProvider) {

    @PostMapping
    fun createAlbum(@PathVariable albumName: String) = campGalleryProvider.createAlbum(albumName)

    @GetMapping("/add")
    fun testAddingPhotos() = campGalleryProvider.testAddingPhotosFlow()
}
