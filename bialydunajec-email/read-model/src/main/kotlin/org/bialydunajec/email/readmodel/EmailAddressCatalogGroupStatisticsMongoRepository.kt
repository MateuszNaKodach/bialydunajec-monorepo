package org.bialydunajec.email.readmodel

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import javax.annotation.PostConstruct

interface EmailAddressCatalogGroupStatisticsMongoRepository : MongoRepository<EmailAddressCatalogGroupStatistics, String>

@Configuration
class BootEmailGroup(val repository: EmailAddressCatalogGroupStatisticsMongoRepository) {

    @PostConstruct
    fun setUp() {
        repository.save(EmailAddressCatalogGroupStatistics())
    }
}