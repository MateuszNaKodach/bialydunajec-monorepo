package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusLineId
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusStop
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusTimetable
import org.bialydunajec.registrations.domain.campbus.valueobject.CoordinatorContact
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

    override fun getVersion() = version

    fun getOrigin() = timetable?.origin
    fun getDestination() = timetable?.destination

    companion object {
        fun createFor(campRegistrationsEditionId: CampRegistrationsEditionId, campBusTimetable: CampBusTimetable) =
                null
    }
}