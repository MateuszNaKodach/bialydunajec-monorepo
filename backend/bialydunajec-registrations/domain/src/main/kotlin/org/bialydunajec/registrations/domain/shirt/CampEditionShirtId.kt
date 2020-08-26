package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import javax.persistence.Embeddable

@Embeddable
class CampEditionShirtId(campEditionNumber: String) : AggregateId(campEditionNumber) {

    constructor(campEditionNumber: Int) : this(campEditionNumber.toString())

    constructor(campRegistrationsEditionId: CampRegistrationsEditionId) : this(campRegistrationsEditionId.toString())


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
