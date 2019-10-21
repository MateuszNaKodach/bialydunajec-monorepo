package org.bialydunajec.email.readmodel.emailwithgroups

import org.springframework.data.mongodb.repository.MongoRepository

internal interface EmailWithGroupsMongoRepository : MongoRepository<EmailWithGroups, String> {
    fun findByAddress(address: String): EmailWithGroups?
}
