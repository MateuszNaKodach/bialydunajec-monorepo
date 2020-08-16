package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class ShirtColorOptionSnapshot(
        @AttributeOverrides(
                AttributeOverride(name = "entityId", column = Column(name = "shirtColorOptionId"))
        )
        val shirtColorOptionId: ShirtColorOptionId,

        val color: Color,

        @Column(name = "color_available")
        val available: Boolean
)