package org.bialydunajec.registrations.rest.v1

import org.bialydunajec.registrations.readmodel.camper.CampParticipantEventStream
import org.bialydunajec.registrations.readmodel.camper.CampParticipantMongoRepository
import org.bialydunajec.registrations.readmodel.payment.PaymentCommitmentMongoRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/rest-api/v1/admin/camp-participant")
@RestController
internal class CampParticipantReadController(
        private val camParticipantMongoRepository: CampParticipantMongoRepository,
        private val campParticipantEventStream: CampParticipantEventStream
) {

    @GetMapping
    fun getAllCampParticipant(@RequestParam campRegistrationsEditionId: String) =
            camParticipantMongoRepository.findAllByCampRegistrationsEditionId(campRegistrationsEditionId)

    @GetMapping("/{campParticipantId}")
    fun getCampParticipantById(@PathVariable campParticipantId: String) =
            camParticipantMongoRepository.findById(campParticipantId)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            campParticipantEventStream.streamingEvents()
}