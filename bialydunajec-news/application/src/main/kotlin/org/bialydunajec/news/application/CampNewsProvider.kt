package org.bialydunajec.news.application

import org.bialydunajec.news.application.dto.CampNewsPageDto

interface CampNewsProvider {

    fun getCampNewsPage(limit: Int? = 30, after: String? = null): CampNewsPageDto
}