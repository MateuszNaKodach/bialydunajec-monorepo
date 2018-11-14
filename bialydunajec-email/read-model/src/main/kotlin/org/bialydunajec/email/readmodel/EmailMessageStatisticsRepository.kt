package org.bialydunajec.email.readmodel

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import javax.annotation.PostConstruct

interface EmailMessageStatisticsRepository : MongoRepository<EmailMessageStatistics, String>


@Configuration
class BootEmailMessageLog(val repository: EmailMessageStatisticsRepository) {


    @PostConstruct
    fun setUp() {
        repository.save(EmailMessageStatistics())
    }
}
