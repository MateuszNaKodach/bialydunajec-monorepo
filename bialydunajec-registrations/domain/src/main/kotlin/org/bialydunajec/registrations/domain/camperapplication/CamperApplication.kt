package org.bialydunajec.registrations.domain.camperapplication

import org.bialydunajec.ddd.domain.aggregate.AggregateRoot
import javax.persistence.Entity

@Entity
class CamperApplication : AggregateRoot<CamperApplicationId, CamperApplicationEvents>(CamperApplicationId()) {
}