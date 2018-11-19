package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusLineId
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusSeatReservationsStatus
import javax.persistence.*

@Entity
@Table(schema = "camp_registrations") //FIXME: Maybe delete word "reservations"
internal class CampBusSeatReservations(

        @Embedded
        val campBusLineId: CampBusLineId,

        var seats: Int?,

        @Embedded
        var oneWayCost: Money?,

        @Enumerated(EnumType.STRING)
        private var status: CampBusSeatReservationsStatus = CampBusSeatReservationsStatus.INACTIVE
)