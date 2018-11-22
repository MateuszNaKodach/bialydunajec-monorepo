package org.bialydunajec.registrations.rest.v1

import org.bialydunajec.registrations.readmodel.statistics.CampRegistrationsEditionStatisticsEventStream
import org.bialydunajec.registrations.readmodel.statistics.CampRegistrationsEditionStatisticsMongoRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/rest-api/v1/admin/camp-registrations-statistics")
@RestController
internal class CampRegistrationsStatisticsReadAdminController(
        private val campRegistrationsEditionStatisticsRepository: CampRegistrationsEditionStatisticsMongoRepository,
        private val eventStream: CampRegistrationsEditionStatisticsEventStream
) {

    @GetMapping("/{campRegistrationsEditionId}")
    fun getAllCampRegistrationsEditionStatistics(@PathVariable campRegistrationsEditionId: String) =
            campRegistrationsEditionStatisticsRepository.findByCampRegistrationsEditionId(campRegistrationsEditionId)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            eventStream.streamingEvents()
}