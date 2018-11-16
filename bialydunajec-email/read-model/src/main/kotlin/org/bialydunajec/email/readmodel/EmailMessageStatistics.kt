package org.bialydunajec.email.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

const val DEFAULT_EMAIL_MESSAGE_STATISTICS_ID: String = "EMAIL_STATISTICS";

@Document("emailMessageStatisticsReadModel")
data class EmailMessageStatistics(
        @Id
        val periodId: String = DEFAULT_EMAIL_MESSAGE_STATISTICS_ID,
        var messagesCount: Long = 0,
        var sentSuccessCount: Long = 0,
        var sentFailureCount: Long = 0
) {

    fun getPendingCount() = messagesCount - (sentSuccessCount + sentFailureCount)
}