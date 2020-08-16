package ogr.bialydunajec.gallery.application.dto

import org.assertj.core.api.Assertions.assertThat
import org.bialydunajec.gallery.application.dto.CampGalleryAlbumDto
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object CampGalleryAlbumDtoTest: Spek ({
    Feature("Mapping album name to display version") {
        Scenario("Successfully mapping") {
            val webAppPrefix = "WebApp_Edycja36_"
            val albumName = "Msze Święte"
            val campGalleryAlbumDto
                    = CampGalleryAlbumDto("id", webAppPrefix + albumName, "url", "url2", 2)

            lateinit var result: CampGalleryAlbumDto
            When("Mapping album title to display version") {
                result = CampGalleryAlbumDto.mapTitleToDisplayVersion(campGalleryAlbumDto)
            }

            Then("Title should be in display version") {
                assertThat(result.title).isEqualTo(albumName)
            }
        }
    }
})
