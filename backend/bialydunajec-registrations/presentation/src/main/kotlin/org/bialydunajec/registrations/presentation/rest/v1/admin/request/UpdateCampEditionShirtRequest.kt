package org.bialydunajec.registrations.presentation.rest.v1.admin.request

import org.bialydunajec.ddd.domain.base.validation.constraints.NullOrNotBlank

data class UpdateCampEditionShirtRequest(
        @field:NullOrNotBlank
        val shirtSizesFileUrl: String?
)