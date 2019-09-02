package org.bialydunajec.gallery.application.dto

data class CampGalleryAlbumDto(
        val id: String,
        var title: String,
        val albumUrl: String,
        val coverPhotoUrl: String,
        val photosCount: Long
) {

    companion object {
        fun mapTitleToDisplayVersion(campGalleryAlbumDto: CampGalleryAlbumDto): CampGalleryAlbumDto {
            campGalleryAlbumDto.title =
                    getCampEditionAlbumRegexHeader().replace(campGalleryAlbumDto.title, "")
            return campGalleryAlbumDto
        }

        private const val webAppPrefixRegex = "WebApp_"
        private const val editionPrefixRegex = "Edycja[0-9]+_"
        private const val albumHeaderRegex = webAppPrefixRegex + editionPrefixRegex
        private const val albumNameRegex = ".+"

        fun getCampEditionAlbumRegexHeader() =
                albumHeaderRegex.toRegex()

        fun getCampEditionAlbumRegex() =
                Regex(albumHeaderRegex + albumNameRegex)

        fun getCampEditionAlbumRegex(edition: String) =
                Regex(webAppPrefixRegex + "Edycja${edition}_" + albumNameRegex)
    }
}
