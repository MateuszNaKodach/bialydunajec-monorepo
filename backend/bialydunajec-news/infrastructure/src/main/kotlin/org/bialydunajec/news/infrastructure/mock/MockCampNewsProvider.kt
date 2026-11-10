package org.bialydunajec.news.infrastructure.mock

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.bialydunajec.news.application.CampNewsProvider
import org.bialydunajec.news.application.dto.CampNewsDto
import org.bialydunajec.news.application.dto.CampNewsPageDto
import org.bialydunajec.news.application.dto.NewsPagingDto
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * Serves a few real posts copied from https://www.facebook.com/bialydunajec, for local runs
 * without a Facebook access token. Images are served by Spring Boot from static/mock-camp-news.
 */
@Component
@ConditionalOnProperty(name = ["bialydunajec.camp-news-provider"], havingValue = "mock")
internal class MockCampNewsProvider(objectMapper: ObjectMapper) : CampNewsProvider {

    private val news: List<CampNewsDto> =
            ClassPathResource("mock-camp-news/camp-news.json").inputStream.use { objectMapper.readValue(it) }

    override fun getCampNewsPage(limit: Int?, after: String?): CampNewsPageDto {
        val baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()
        val content = news.map { it.copy(picture = it.picture?.let { p -> p.copy(url = baseUrl + p.url) }) }
        return CampNewsPageDto(content, NewsPagingDto(null))
    }
}
