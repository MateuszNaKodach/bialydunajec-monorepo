package org.bialydunajec.gallery.application.dto

data class CampGalleryAlbumDto(
        val id: String,
        val title: String,
        val albumUrl: String,
        val coverPhotoUrl: String,
        val photosCount: Long
) {
    init {
        getCampEditionAlbumRegexHeader().replace(title, "")
    }

    companion object {
        private const val campEditionAlbumRegexHeader = "WebApp_Edycja[0-9]+_"

        fun getCampEditionAlbumRegex() =
                "$campEditionAlbumRegexHeader.+".toRegex()
    }

    private fun getCampEditionAlbumRegexHeader() =
            campEditionAlbumRegexHeader.toRegex()
}
