package org.bialydunajec.email.readmodel.rest

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

const val DEFAULT_EMAIL_MESSAGE_STATISTICS_ID = 0L;

@Document
data class EmailMessageStatistics(
        @Id
        var periodId: Long = DEFAULT_EMAIL_MESSAGE_STATISTICS_ID,
        var messagesCount: Long = 0,
        var pendingCount: Long = 0,
        var sentSuccessCount: Long = 0,
        var sentFailureCount: Long = 0
)