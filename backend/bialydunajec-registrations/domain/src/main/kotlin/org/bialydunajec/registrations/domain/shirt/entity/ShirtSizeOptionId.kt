package org.bialydunajec.registrations.domain.shirt.entity

import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import javax.persistence.Embeddable

@Embeddable
class ShirtSizeOptionId(shirtSizeOptionId: String = defaultValue()) : EntityId(shirtSizeOptionId)