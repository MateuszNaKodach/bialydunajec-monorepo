package org.bialydunajec.registrations.rest.v1

import org.bialydunajec.registrations.readmodel.payment.PaymentCommitmentMongoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/payment-commitment")
@RestController
internal class PaymentCommitmentReadAdminController(
        private val paymentCommitmentRepository: PaymentCommitmentMongoRepository
) {

    @GetMapping
    fun getAllPaymentCommitment(@RequestParam campRegistrationsEditionId: String) =
            paymentCommitmentRepository.findAllByCampRegistrationsEditionId(campRegistrationsEditionId)

}