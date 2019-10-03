package org.bialydunajec.email.readmodel.emailgroup

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

const val DEFAULT_EMAIL_GROUP_STATISTICS_ID: String = "EMAIL_GROUP_STATISTICS";

@Document("emailGroupStatisticsReadModel")
data class EmailGroupStatistics(
        @Id
        val periodId: String = DEFAULT_EMAIL_GROUP_STATISTICS_ID,
        var groupsCount: Long = 0
)
