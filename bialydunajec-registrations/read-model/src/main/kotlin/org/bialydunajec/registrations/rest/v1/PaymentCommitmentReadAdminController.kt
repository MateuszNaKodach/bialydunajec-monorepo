package org.bialydunajec.registrations.rest.v1

import org.bialydunajec.registrations.readmodel.payment.CampParticipantCottageAccountEventStream
import org.bialydunajec.registrations.readmodel.payment.PaymentCommitmentMongoRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/payment-commitment")
@RestController
internal class PaymentCommitmentReadAdminController(
        private val paymentCommitmentRepository: PaymentCommitmentMongoRepository,
        private val campParticipantCottageAccountEventStream: CampParticipantCottageAccountEventStream
) {

    @GetMapping
    fun getAllPaymentCommitment(@RequestParam campRegistrationsEditionId: String) =
            paymentCommitmentRepository.findAllByCampRegistrationsEditionId(campRegistrationsEditionId)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            campParticipantCottageAccountEventStream.streamingEvents()

}