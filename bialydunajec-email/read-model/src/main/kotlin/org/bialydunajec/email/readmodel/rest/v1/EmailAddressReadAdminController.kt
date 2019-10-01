package org.bialydunajec.email.readmodel.rest.v1

import org.bialydunajec.email.readmodel.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import kotlin.streams.toList

@RequestMapping("/rest-api/v1/admin/email-address")
@RestController
internal class EmailAddressReadAdminController(
        private val emailAddressMongoRepository: EmailAddressMongoRepository,
        private val emailAddressStatisticsMongoRepository: EmailAddressStatisticsMongoRepository,
        private val emailAddressEventStream: EmailAddressEventStream
) {


    @GetMapping("/statistics")
    fun getEmailAddressStatistics() =
            emailAddressStatisticsMongoRepository.findById(DEFAULT_EMAIL_ADDRESS_STATISTICS_ID)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            emailAddressEventStream.streamingEvents()

    @GetMapping
    fun getEmailAddresses(@RequestParam(required = false) groupName: String?, ownerFirstName: String?, ownerLastName: String?): Collection<EmailAddress> {
        var foundEmailAddresses = emailAddressMongoRepository.findAll()

        groupName?.let {
            foundEmailAddresses = foundEmailAddresses.filter { it.emailGroupName.equals(groupName) }
        }

        ownerFirstName?.let {
            foundEmailAddresses = foundEmailAddresses.filter { it.ownerFirstName.equals(ownerFirstName) }
        }

        ownerLastName?.let {
            foundEmailAddresses = foundEmailAddresses.filter { it.ownerLastName.equals(ownerLastName) }
        }
        return foundEmailAddresses.sortedByDescending { it.emailAddress }
    }

}