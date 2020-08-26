package org.bialydunajec.registrations.domain.campedition.entity

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.EntityId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import javax.persistence.Embeddable

@Embeddable
class CampRegistrationsId(campRegistrationsEditionId: CampRegistrationsEditionId) : EntityId(campRegistrationsEditionId.toString())
