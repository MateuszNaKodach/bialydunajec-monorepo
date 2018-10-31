package org.bialydunajec.registrations.readmodel.sample

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import javax.annotation.PostConstruct

interface JiraProjectRepository : MongoRepository<JiraProject, String>

@Configuration
class Boot(val jiraProjectRepository: JiraProjectRepository) {


    @PostConstruct
    fun setUp() {
        jiraProjectRepository.save(JiraProject(name = "test"))
    }
}