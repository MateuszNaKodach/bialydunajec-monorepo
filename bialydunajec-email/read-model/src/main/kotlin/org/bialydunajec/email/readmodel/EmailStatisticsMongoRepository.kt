package org.bialydunajec.email.readmodel

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import javax.annotation.PostConstruct

interface EmailStatisticsMongoRepository : MongoRepository<EmailStatistics, String>

@Configuration
class BootEmailAddress(val repository: EmailStatisticsMongoRepository) {

    @PostConstruct
    fun setUp() {
        repository.save(EmailStatistics())
    }
}
