package org.bialydunajec.email.readmodel.rest.v1

import org.bialydunajec.email.readmodel.EmailMessageRepository
import org.bialydunajec.email.readmodel.EmailMessageStatisticsRepository
import org.bialydunajec.email.readmodel.DEFAULT_EMAIL_MESSAGE_STATISTICS_ID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/email-message")
@RestController
internal class EmailMessageReadAdminController(
        private val emailMessageRepository: EmailMessageRepository,
        private val emailMessageStatisticsRepository: EmailMessageStatisticsRepository
) {

    @GetMapping
    fun getAllEmailMessage() = emailMessageRepository.findAll()
            .sortByDescending { it.createdDate }

    @GetMapping("/statistics")
    fun getEmailMessagesStatistics() =
            emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)


}