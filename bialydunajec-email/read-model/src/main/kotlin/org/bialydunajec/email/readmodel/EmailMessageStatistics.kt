package org.bialydunajec.email.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

const val DEFAULT_EMAIL_MESSAGE_STATISTICS_ID: String = "EMAIL_STATISTICS";

@Document
data class EmailMessageStatistics(
        @Id
        val periodId: String = DEFAULT_EMAIL_MESSAGE_STATISTICS_ID,
        var messagesCount: Long = 0,
        var pendingCount: Long = 0,
        var sentSuccessCount: Long = 0,
        var sentFailureCount: Long = 0
)