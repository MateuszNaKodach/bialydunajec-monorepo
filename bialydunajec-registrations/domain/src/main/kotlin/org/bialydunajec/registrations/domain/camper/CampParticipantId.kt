package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.springframework.stereotype.Component
import javax.persistence.Embeddable

interface PeselEncoder {
    fun encode(pesel: Pesel): String
    fun match(rawPesel: Pesel, encodedPesel: Pesel): Boolean
}

@Component
class CampParticipantIdGenerator(private val peselEncoder: PeselEncoder) {
    fun generateFrom(pesel: Pesel?) =
            CampParticipantId(pesel?.let { peselEncoder.encode(it) } ?: AggregateId.defaultValue())
}

@Embeddable
class CampParticipantId internal constructor(campParticipantId: String) : AggregateId(campParticipantId)