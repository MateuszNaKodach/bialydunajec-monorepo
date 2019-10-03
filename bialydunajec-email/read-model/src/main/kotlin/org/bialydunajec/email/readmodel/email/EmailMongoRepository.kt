package org.bialydunajec.email.readmodel.email

import org.springframework.data.mongodb.repository.MongoRepository

internal interface EmailMongoRepository : MongoRepository<Email, String> {
}
