package org.bialydunajec.registrations.application.dto

import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSizeOptionSnapshot

data class CampEditionShirtDto(
        val campRegistrationsEditionId: String,
        val shirtSizesFileUrl: String?,
        val colorOptions: Collection<ShirtColorOptionDto>,
        val sizeOptions: Collection<ShirtSizeOptionDto>
)