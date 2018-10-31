package org.bialydunajec.registrations.readmodel.sample

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.validation.constraints.NotEmpty

@Document
data class JiraProject(
        @Id
        val id: String = UUID.randomUUID().toString(),
        @NotEmpty
        val name: String,
        val active: Boolean = true
)