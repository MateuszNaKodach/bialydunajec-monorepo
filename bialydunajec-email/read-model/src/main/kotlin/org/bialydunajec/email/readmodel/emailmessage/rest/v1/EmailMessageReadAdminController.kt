package org.bialydunajec.email.readmodel.emailmessage.rest.v1

import org.bialydunajec.email.readmodel.emailmessage.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/email-message")
@RestController
internal class EmailMessageReadAdminController(
    private val emailMessageRepository: EmailMessageMongoRepository,
    private val emailMessageStatisticsRepository: EmailMessageStatisticsMongoRepository,
    private val emailMessageLogEventStream: EmailMessageLogEventStream
) {

    @GetMapping
    fun getAllEmailMessage(): Collection<EmailMessage> = emailMessageRepository.findAll()
            .sortedByDescending { it.createdDate }

    @GetMapping("/statistics")
    fun getEmailMessagesStatistics() =
            emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            emailMessageLogEventStream.streamingEvents()

}
