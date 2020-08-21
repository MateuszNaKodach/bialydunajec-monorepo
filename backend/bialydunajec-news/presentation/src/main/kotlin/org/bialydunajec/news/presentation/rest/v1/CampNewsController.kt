package org.bialydunajec.news.presentation.rest.v1

import org.bialydunajec.news.application.CampNewsProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api/v1/camp-news")
internal class CampNewsController(private val campNewsProvider: CampNewsProvider) {

    @GetMapping
    fun getLastCampNews() = campNewsProvider.getCampNewsPage()

}