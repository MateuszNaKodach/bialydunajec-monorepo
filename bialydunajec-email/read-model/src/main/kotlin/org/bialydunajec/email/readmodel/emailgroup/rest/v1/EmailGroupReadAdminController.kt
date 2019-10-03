package org.bialydunajec.email.readmodel.emailgroup.rest.v1

import org.bialydunajec.email.readmodel.emailgroup.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/email-group")
@RestController
internal class EmailGroupReadAdminController(
    private val emailGroupMongoRepository: EmailGroupMongoRepository,
    private val emailGroupStatisticsMongoRepository: EmailGroupStatisticsMongoRepository,
    private val emailGroupEventStream: EmailGroupEventStream
) {

    @GetMapping
    fun getAllEmailAddressCatalogGroups(): Collection<EmailGroup> =
            emailGroupMongoRepository.findAll()
                    .sortedByDescending { it.groupName }

    @GetMapping("/statistics")
    fun getEmailAddressCatalogGroupStatistics() =
            emailGroupStatisticsMongoRepository.findById(DEFAULT_EMAIL_GROUP_STATISTICS_ID)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            emailGroupEventStream.streamingEvents()

}
