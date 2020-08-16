package org.bialydunajec.news.application.dto

data class CampNewsPageDto(
        val content: Collection<CampNewsDto>,
        val paging: NewsPagingDto
)

data class NewsPagingDto(
        val cursors: PagingCursorsDto?
)

data class PagingCursorsDto(
        val before: String?,
        val after: String?
)