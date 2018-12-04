package org.bialydunajec.registrations.dto

data class CampEditionShirtDto(
        val campEditionShirtId: String,
        val campRegistrationsEditionId: String,
        val shirtSizesFileUrl: String?,
        val colorOptions: Collection<ShirtColorOptionDto>,
        val sizeOptions: Collection<ShirtSizeOptionDto>
)