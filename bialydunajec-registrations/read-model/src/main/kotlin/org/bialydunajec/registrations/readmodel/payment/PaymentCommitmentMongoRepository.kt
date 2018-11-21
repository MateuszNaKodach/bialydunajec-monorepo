package org.bialydunajec.registrations.readmodel.payment

import org.springframework.data.mongodb.repository.MongoRepository

internal interface PaymentCommitmentMongoRepository : MongoRepository<PaymentCommitment, String> {

    fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: String): List<PaymentCommitment>

}
