package org.bialydunajec.registrations.readmodel

import org.springframework.data.mongodb.repository.MongoRepository

internal interface PaymentCommitmentMongoRepository : MongoRepository<PaymentCommitment, String> {
    fun findByIdOrCreate(paymentCommitmentId: String) =
            findById(paymentCommitmentId).orElseGet { PaymentCommitment.onlyId(paymentCommitmentId) }
}
