package org.bialydunajec.email.readmodel.emailgroup

import org.springframework.data.mongodb.repository.MongoRepository

internal interface EmailGroupMongoRepository : MongoRepository<EmailGroup, String> {
}
