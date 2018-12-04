package org.bialydunajec.registrations.domain.campbus.entity

import org.bialydunajec.ddd.domain.base.persistence.AuditableEntity
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Version

/**
 * Na autokar mogą zapisać się tylko uczestnicy Obozu
 */
@Entity
@Table(schema = "camp_registrations")
internal class CampBusReservation(
        val campParticipantId: CampParticipantId
) : AuditableEntity<CampBusReservationId>(), Versioned {

    @EmbeddedId
    override val entityId: CampBusReservationId = CampBusReservationId()


    @Version
    private var version: Long? = null

    override fun getVersion() = version

}