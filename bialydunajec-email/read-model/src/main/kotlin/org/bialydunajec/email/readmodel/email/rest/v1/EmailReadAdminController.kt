package org.bialydunajec.email.readmodel.email.rest.v1

import org.bialydunajec.email.readmodel.email.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/email")
@RestController
internal class EmailReadAdminController(
    private val emailMongoRepository: EmailMongoRepository,
    private val emailStatisticsMongoRepository: EmailStatisticsMongoRepository,
    private val emailEventStream: EmailEventStream
) {

    @GetMapping("/statistics")
    fun getEmailStatistics() =
            emailStatisticsMongoRepository.findById(DEFAULT_EMAIL_STATISTICS_ID)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            emailEventStream.streamingEvents()

    @GetMapping
    fun getEmails(@RequestParam(required = true) groupId: String, @RequestParam(required = false)  ownerFirstName: String?, @RequestParam(required = false) ownerLastName: String?, @RequestParam(required = false) emailAddress: String?): Collection<Email> {
        var foundEmailAddresses = emailMongoRepository.findAll()

        groupId?.let {
            foundEmailAddresses = foundEmailAddresses.filter { it.emailGroupName.equals(groupId) }
        }

        ownerFirstName?.let {
            foundEmailAddresses = foundEmailAddresses.filter { it.ownerFirstName.equals(ownerFirstName) }
        }

        ownerLastName?.let {
            foundEmailAddresses = foundEmailAddresses.filter { it.ownerLastName.equals(ownerLastName) }
        }
        return foundEmailAddresses.sortedByDescending { it.address }
    }

}
