package org.bialydunajec.registrations.readmodel.rest.v1

import org.bialydunajec.registrations.readmodel.PaymentCommitmentMongoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/payment-commitment")
@RestController
internal class PaymentCommitmentReadController(
        private val paymentCommitmentRepository: PaymentCommitmentMongoRepository
) {

    @GetMapping
    fun getAllPaymentCommitment(@RequestParam campRegistrationsEditionId: String) =
            paymentCommitmentRepository.findAllByCampRegistrationsEditionId(campRegistrationsEditionId)

}