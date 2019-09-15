package org.bialydunajec.email.readmodel.rest.v1

import org.bialydunajec.email.readmodel.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.streams.toList

@RequestMapping("/rest-api/v1/admin/email-address")
@RestController
internal class EmailAddressReadAdminController(
        private val emailAddressMongoRepository: EmailAddressMongoRepository,
        private val emailAddressCatalogGroupMongoRepository: EmailAddressCatalogGroupMongoRepository,
        private val emailAddressStatisticsMongoRepository: EmailAddressStatisticsMongoRepository,
        private val emailAddressEventStream: EmailAddressEventStream
) {

    @GetMapping
    fun getAllEmailAddresses(): Collection<EmailAddress> = emailAddressMongoRepository.findAll()
            .sortedByDescending { it.emailAddress }

    @GetMapping("/statistics")
    fun getEmailAddressStatistics() =
            emailAddressStatisticsMongoRepository.findById(DEFAULT_EMAIL_ADDRESS_STATISTICS_ID)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            emailAddressEventStream.streamingEvents()

    @GetMapping("/{groupName}")
    fun getEmailAddressByGroupName(@PathVariable groupName: String) =
            emailAddressMongoRepository.findAllById(
                    emailAddressCatalogGroupMongoRepository.findAll().filter { it.groupName.equals(groupName) }.stream().map { it.emailGroupId }.toList())


}