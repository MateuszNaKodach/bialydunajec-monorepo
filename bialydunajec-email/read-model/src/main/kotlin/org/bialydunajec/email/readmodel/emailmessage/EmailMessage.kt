package org.bialydunajec.email.readmodel.emailmessage

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

//TODO: Dodac autora wiadomosci, jesli nie bedzie nim nikt, wiadomo, ze system.
@Document("emailMessageLogReadModels")
data class EmailMessage(
        @Id
        var emailMessageLogId: String,
        var recipient: String? = null,
        var subject: String? = null,
        var content: String? = null,
        var status: String? = null,
        var lastError: String? = null,

        @JsonFormat(timezone="Europe/Warsaw")
        var sentDate: Instant? = null,

        @JsonFormat(timezone="Europe/Warsaw")
        var createdDate: Instant? = null
)
