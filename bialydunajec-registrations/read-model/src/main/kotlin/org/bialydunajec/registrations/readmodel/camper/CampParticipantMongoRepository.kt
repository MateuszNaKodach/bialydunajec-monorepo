package org.bialydunajec.registrations.readmodel.camper

import org.springframework.data.mongodb.repository.MongoRepository

internal interface CampParticipantMongoRepository : MongoRepository<CampParticipant, String> {

    fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: String): List<CampParticipant>

}
