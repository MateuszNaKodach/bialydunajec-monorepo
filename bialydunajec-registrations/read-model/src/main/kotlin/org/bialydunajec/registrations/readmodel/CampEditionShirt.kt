package org.bialydunajec.registrations.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class CampEditionShirt (
        @Id
        val campEditionId: String
)