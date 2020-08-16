package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId

class CamperShirtOrder (
        val shirtColorOptionId: ShirtColorOptionId,
        val shirtSizeOptionId: ShirtSizeOptionId
)