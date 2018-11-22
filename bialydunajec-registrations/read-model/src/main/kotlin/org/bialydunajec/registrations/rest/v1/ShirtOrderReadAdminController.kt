package org.bialydunajec.registrations.rest.v1

import org.bialydunajec.registrations.readmodel.shirt.ShirtOrderEventStream
import org.bialydunajec.registrations.readmodel.shirt.ShirtOrderMongoRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/shirt-order")
@RestController
internal class ShirtOrderReadAdminController(
        private val shirtOrderMongoRepository: ShirtOrderMongoRepository,
        private val shirtOrderEventStream: ShirtOrderEventStream
) {

    @GetMapping
    fun getAllCampParticipant(@RequestParam campRegistrationsEditionId: String) =
            shirtOrderMongoRepository.findAllByCampRegistrationsEditionId(campRegistrationsEditionId)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            shirtOrderEventStream.streamingEvents()
}