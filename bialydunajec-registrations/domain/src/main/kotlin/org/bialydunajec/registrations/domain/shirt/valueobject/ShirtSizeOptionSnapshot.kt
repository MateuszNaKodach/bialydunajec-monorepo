package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class ShirtSizeOptionSnapshot(
        @AttributeOverrides(
                AttributeOverride(name = "entityId", column = Column(name = "shirtSizeOptionId"))
        )
        val shirtSizeOptionId: ShirtSizeOptionId,

        val size: ShirtSize,

        @Column(name = "size_available")
        val available: Boolean
)