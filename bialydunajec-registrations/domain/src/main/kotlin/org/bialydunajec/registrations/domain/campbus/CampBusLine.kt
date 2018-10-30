package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusLineId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import javax.persistence.*

@Entity
@Table(schema = "camp_registrations")
class CampBusLine(
        campBusId: CampBusLineId,
        @Embedded
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val busName: String?,
        val description: String?,
        val oneWayCost: Money
) : AuditableAggregateRoot<CampBusLineId, CampBusEvent>(campBusId), Versioned {

    @Version
    private var version: Long? = null

    override fun getVersion() = version

}