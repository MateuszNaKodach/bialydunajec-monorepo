package org.bialydunajec.registrations.presentation.rest.v1.admin.request

import org.bialydunajec.ddd.domain.base.validation.constraints.NullOrNotBlank
import org.bialydunajec.registrations.application.dto.ColorDto
import org.bialydunajec.registrations.application.dto.ShirtSizeDto

data class AddCampEditionShirtSizeRequest(
        val size: ShirtSizeDto
)