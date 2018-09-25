package org.bialydunajec.registrations.domain.camperapplication

import org.bialydunajec.ddd.domain.aggregate.AggregateRoot

class CamperApplication : AggregateRoot<CamperApplicationId, CamperApplicationEvents>(CamperApplicationId()) {
}