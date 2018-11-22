package org.bialydunajec.registrations.domain.camper.campparticipant

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.springframework.stereotype.Component
import javax.persistence.Column
import javax.persistence.Embeddable

interface PeselEncoder {
    fun encode(pesel: Pesel): String
    fun match(rawPesel: Pesel, encodedPesel: Pesel): Boolean
}

@Component
class CamperTrackingCodeGenerator(private val peselEncoder: PeselEncoder) {
    fun generateFrom(pesel: Pesel?) =
            CamperTrackingCode(pesel?.let { peselEncoder.encode(it) }
                    ?: AggregateId.defaultValue())
}

@Embeddable
class CamperTrackingCode(private val camperTrackingCode: String) {
    override fun toString() = camperTrackingCode
}

@Embeddable
class CampParticipantId : AggregateId()