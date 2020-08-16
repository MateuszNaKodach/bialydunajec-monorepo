package org.bialydunajec.news.application.dto

import java.time.Instant

// https://stackoverflow.com/questions/32437550/whats-the-difference-between-instant-and-localdatetime
data class CampNewsDto(
        val id: String?,
        val message: String?,
        val picture: NewsPictureDto?,
        val createdDate: Instant?
)

data class NewsPictureDto(
        val url: String?,
        val name: String?,
        val description: String?
)