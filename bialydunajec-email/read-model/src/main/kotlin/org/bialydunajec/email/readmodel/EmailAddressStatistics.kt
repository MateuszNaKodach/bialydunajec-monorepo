package org.bialydunajec.email.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

const val DEFAULT_EMAIL_ADDRESS_STATISTICS_ID: String = "EMAIL_ADDRESS_STATISTICS";

@Document("emailAddressStatisticsReadModel")
data class EmailAddressStatistics(
        @Id
        val periodId: String = DEFAULT_EMAIL_ADDRESS_STATISTICS_ID,
        var addressesCount: Long = 0
)
