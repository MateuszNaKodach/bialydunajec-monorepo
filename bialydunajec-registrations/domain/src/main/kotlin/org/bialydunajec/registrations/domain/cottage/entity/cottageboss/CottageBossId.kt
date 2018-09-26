package org.bialydunajec.registrations.domain.cottage.entity.cottageboss

import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.Embeddable

@Embeddable
class CottageBossId(cottageId: CottageId) : EntityId(cottageId.toString())