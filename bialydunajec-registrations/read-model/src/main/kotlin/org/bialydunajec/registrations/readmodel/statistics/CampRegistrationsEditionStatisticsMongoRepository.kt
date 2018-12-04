package org.bialydunajec.registrations.readmodel.statistics

import org.springframework.data.mongodb.repository.MongoRepository

internal interface CampRegistrationsEditionStatisticsMongoRepository : MongoRepository<CampRegistrationsEditionStatistics, String> {

    fun findByCampRegistrationsEditionId(campRegistrationsEditionId: String): CampRegistrationsEditionStatistics?

}