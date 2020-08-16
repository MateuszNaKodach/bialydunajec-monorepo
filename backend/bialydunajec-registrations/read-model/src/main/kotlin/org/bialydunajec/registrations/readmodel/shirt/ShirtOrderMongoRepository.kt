package org.bialydunajec.registrations.readmodel.shirt

import org.springframework.data.mongodb.repository.MongoRepository

internal interface ShirtOrderMongoRepository : MongoRepository<ShirtOrder, String> {
    fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: String): List<ShirtOrder>
    fun findAllByCampParticipantCampParticipantId(campParticipantId: String): List<ShirtOrder>
}
