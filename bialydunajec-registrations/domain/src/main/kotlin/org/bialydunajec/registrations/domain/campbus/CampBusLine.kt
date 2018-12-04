package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.campbus.valueobject.*
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import javax.persistence.*

@Entity
@Table(schema = "camp_registrations")
class CampBusLine internal constructor(
        campBusLineId: CampBusLineId,

        @Embedded
        val campRegistrationsEditionId: CampRegistrationsEditionId,

        var busName: String?,

        @Lob
        var description: String?,

        @Lob
        var additionalNotes: String?,

        @Embedded
        var coordinatorContact: CoordinatorContact?,

        @Embedded
        private var timetable: CampBusTimetable? = null
) : AuditableAggregateRoot<CampBusLineId, CampBusEvent>(campBusLineId), Versioned {


    @Version
    private var version: Long? = null

   fun createSeatReservations(seats: Int? = null, oneWayCost: Money? = null, status: CampBusSeatReservationsStatus = CampBusSeatReservationsStatus.INACTIVE) =
            CampBusSeatReservations(getAggregateId(), seats, oneWayCost, status)


    override fun getVersion() = version

    fun getOrigin() = timetable?.origin
    fun getDestination() = timetable?.destination

    companion object {
        fun createFor(campRegistrationsEditionId: CampRegistrationsEditionId, campBusTimetable: CampBusTimetable) =
                null
    }
}