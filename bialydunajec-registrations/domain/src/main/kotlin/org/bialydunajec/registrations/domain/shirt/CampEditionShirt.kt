package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId

class CampEditionShirt(
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val shirtSizesFileUrl: Url?
) : AuditableAggregateRoot<CampEditionShirtId, CampEditionShirtEvent>(CampEditionShirtId(campRegistrationsEditionId)) {


}