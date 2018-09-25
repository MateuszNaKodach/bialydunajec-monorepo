package org.bialydunajec.registrations.domain.camperapplication

import org.bialydunajec.ddd.domain.aggregate.AggregateRoot
import javax.persistence.Entity

@Entity
class CamperApplication(
        val name: String
) : AggregateRoot<CamperApplicationId, CamperApplicationEvents>(CamperApplicationId()) {
}