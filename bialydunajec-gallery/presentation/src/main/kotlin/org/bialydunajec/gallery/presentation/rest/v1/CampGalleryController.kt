package org.bialydunajec.gallery.presentation.rest.v1

import org.bialydunajec.gallery.application.CampGalleryProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/camp-gallery")
internal class CampGalleryController(private val campGalleryProvider: CampGalleryProvider) {

    @GetMapping
    fun getAlbumList() = campGalleryProvider.getAlbumList()

    @PostMapping
    fun createAlbum(@PathVariable albumName: String) = campGalleryProvider.createAlbum(albumName)
}
