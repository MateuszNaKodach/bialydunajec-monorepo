package org.bialydunajec.gallery.application

interface CampGalleryProvider {

    fun getAlbumList()
    fun createAlbum(albumName: String)
}
