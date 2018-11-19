package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.campbus.entity.CampBusReservation
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusLineId
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusSeatReservationsId
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusSeatReservationsStatus
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import javax.persistence.*

@Entity
@Table(schema = "camp_registrations") //FIXME: Maybe delete word "reservations"
class CampBusSeatReservations internal constructor(

        @Embedded
        val campBusLineId: CampBusLineId,

        var seats: Int?,

        @Embedded
        var oneWayCost: Money?,

        @Enumerated(EnumType.STRING)
        private var status: CampBusSeatReservationsStatus = CampBusSeatReservationsStatus.INACTIVE
) : AuditableAggregateRoot<CampBusSeatReservationsId, CampBusSeatReservationsEvent>(CampBusSeatReservationsId()), Versioned {

    @OneToMany(cascade = [CascadeType.ALL])
    private var reservations: MutableList<CampBusReservation> = mutableListOf()


    fun reserveSeatFor(campParticipant: CampParticipant) {
        reservations.add(CampBusReservation(campParticipant.getAggregateId()))
    }

    @Version
    private var version: Long? = null

    override fun getVersion() = version
}