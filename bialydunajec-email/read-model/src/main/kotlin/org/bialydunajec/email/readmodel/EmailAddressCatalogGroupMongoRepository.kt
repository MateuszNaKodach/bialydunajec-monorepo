package org.bialydunajec.email.readmodel

import org.springframework.data.mongodb.repository.MongoRepository

internal interface EmailAddressCatalogGroupMongoRepository : MongoRepository<EmailAddressCatalogGroup, String> {
}