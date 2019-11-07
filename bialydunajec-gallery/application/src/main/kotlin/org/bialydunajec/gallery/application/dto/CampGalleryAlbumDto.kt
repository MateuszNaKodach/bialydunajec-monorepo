package org.bialydunajec.gallery.application.dto

data class CampGalleryAlbumDto(
    val id: String,
    var title: String,
    val albumUrl: String,
    val coverPhotoUrl: String,
    val photosCount: Long
) {

    companion object {
        private const val WEB_APP_PREFIX = "WebApp_"
        private const val EDITION_PREFIX = "Edycja[0-9]+_"
        private const val ALBUM_HEADER = WEB_APP_PREFIX + EDITION_PREFIX
        private const val ALBUM_NAME = ".+"

        fun mapTitleToDisplayVersion(campGalleryAlbumDto: CampGalleryAlbumDto): CampGalleryAlbumDto {
            campGalleryAlbumDto.title =
                getCampEditionAlbumRegexHeader().replace(campGalleryAlbumDto.title, "")
            return campGalleryAlbumDto
        }

        private fun getCampEditionAlbumRegexHeader() =
            ALBUM_HEADER.toRegex()

        fun getCampEditionAlbumRegex() =
            Regex(ALBUM_HEADER + ALBUM_NAME)

        fun getCampEditionAlbumRegex(edition: String) =
            Regex(WEB_APP_PREFIX + "Edycja${edition}_" + ALBUM_NAME)
    }
}
