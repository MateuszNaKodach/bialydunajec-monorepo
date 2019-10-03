package org.bialydunajec.email.readmodel.groupwithemails.rest.v1

import org.bialydunajec.email.readmodel.groupwithemails.GroupWithEmails
import org.bialydunajec.email.readmodel.groupwithemails.GroupWithEmailsEventStream
import org.bialydunajec.email.readmodel.groupwithemails.GroupWithEmailsMongoRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/email-group")
@RestController
internal class EmailGroupReadAdminController(
    private val emailGroupMongoRepository: GroupWithEmailsMongoRepository,
    private val emailGroupStatisticsMongoRepository: EmailGroupStatisticsMongoRepository,
    private val emailGroupEventStream: GroupWithEmailsEventStream
) {

    @GetMapping
    fun getAllEmailAddressCatalogGroups(): Collection<GroupWithEmails> =
            emailGroupMongoRepository.findAll()
                    .sortedByDescending { it.groupName }

    @GetMapping("/statistics")
    fun getEmailAddressCatalogGroupStatistics() =
            emailGroupStatisticsMongoRepository.findById(DEFAULT_EMAIL_GROUP_STATISTICS_ID)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            emailGroupEventStream.streamingEvents()

}
