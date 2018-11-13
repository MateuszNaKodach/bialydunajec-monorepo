package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
class OrderedShirt(
        @Embedded
        val color: OrderedColor,
        @Embedded
        val size: OrderedSize
)

@Embeddable
data class OrderedColor(
        @Embedded
        val shirtColorOptionId: ShirtColorOptionId,
        @Embedded
        val color: Color
)

@Embeddable
data class OrderedSize(
        @Embedded
        val shirtSizeOptionId: ShirtSizeOptionId,
        @Embedded
        val size: ShirtSize
)