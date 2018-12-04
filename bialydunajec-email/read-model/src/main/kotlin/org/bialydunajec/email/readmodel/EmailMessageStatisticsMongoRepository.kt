package org.bialydunajec.email.readmodel

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import javax.annotation.PostConstruct

interface EmailMessageStatisticsMongoRepository : MongoRepository<EmailMessageStatistics, String>


@Configuration
class BootEmailMessageLog(val repository: EmailMessageStatisticsMongoRepository) {


    @PostConstruct
    fun setUp() {
        repository.save(EmailMessageStatistics())
    }
}
