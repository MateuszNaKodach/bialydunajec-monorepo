package org.bialydunajec.registrations.domain.cottageaccommodation

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.CottageId

class CottageAccommodationId(campRegistrationsId: CampRegistrationsId, cottageId: CottageId)
    : AggregateId("$campRegistrationsId-$cottageId")