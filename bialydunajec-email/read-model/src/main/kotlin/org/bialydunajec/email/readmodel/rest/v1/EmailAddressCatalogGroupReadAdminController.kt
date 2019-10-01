package org.bialydunajec.email.readmodel.rest.v1

import org.bialydunajec.email.readmodel.*
import org.bialydunajec.email.readmodel.EmailMessageLogEventStream
import org.bialydunajec.email.readmodel.EmailMessageMongoRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/email-address-catalog-group")
@RestController
internal class EmailAddressCatalogGroupReadAdminController(
        private val emailAddressCatalogGroupMongoRepository: EmailAddressCatalogGroupMongoRepository,
        private val emailAddressCatalogGroupStatisticsMongoRepository: EmailAddressCatalogGroupStatisticsMongoRepository,
        private val emailAddressCatalogGroupEventStream: EmailAddressCatalogGroupEventStream
) {

    @GetMapping
    fun getAllEmailAddressCatalogGroups(): Collection<EmailAddressCatalogGroup> =
            emailAddressCatalogGroupMongoRepository.findAll()
                    .sortedByDescending { it.groupName }

    @GetMapping("/statistics")
    fun getEmailAddressCatalogGroupStatistics() =
            emailAddressCatalogGroupStatisticsMongoRepository.findById(DEFAULT_EMAIL_ADDRESS_CATALOG_GROUP_STATISTICS_ID)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
            emailAddressCatalogGroupEventStream.streamingEvents()

}