package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.aggregate.AggregateRoot
import javax.persistence.Entity

@Entity
class CampEdition(campEditionId: CampEditionId) : AggregateRoot<CampEditionId, CampEditionEvents>(campEditionId)