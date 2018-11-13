package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId

data class ShirtSizeOptionSnapshot (
       val shirtSizeOptionId: ShirtSizeOptionId,
       val size: ShirtSize,
       val available: Boolean = true
)