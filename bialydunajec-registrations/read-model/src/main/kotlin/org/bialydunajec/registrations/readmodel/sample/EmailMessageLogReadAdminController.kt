package org.bialydunajec.registrations.readmodel.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/jira-projects")
@RestController
internal class JiraRepoController(private val repository: JiraProjectRepository) {

    @GetMapping
    fun getAllEmailMessageLog() = repository.findAll()

}