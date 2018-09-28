package org.bialydunajec.registrations.domain.campedition.entity

import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import javax.persistence.Embeddable

@Embeddable
class CampRegistrationsId(campEditionId: CampEditionId) : EntityId(campEditionId.toString())