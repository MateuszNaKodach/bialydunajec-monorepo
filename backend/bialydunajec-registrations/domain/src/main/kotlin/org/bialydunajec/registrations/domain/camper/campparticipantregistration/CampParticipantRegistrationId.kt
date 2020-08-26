package org.bialydunajec.registrations.domain.camper.campparticipantregistration

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import javax.persistence.Embeddable

@Embeddable
class CampParticipantRegistrationId(campParticipantRegistrationId: String) : AggregateId(campParticipantRegistrationId) {
    constructor(campRegistrationsEditionId: CampRegistrationsEditionId) : this("$campRegistrationsEditionId-${defaultValue()}")
}
