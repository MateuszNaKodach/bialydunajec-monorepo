package org.bialydunajec.registrations.infrastructure.camper

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.domain.camper.PeselEncoder
import org.springframework.stereotype.Component

@Component
internal class BCryptPeselEncoder : PeselEncoder {

    override fun encode(pesel: Pesel): String = pesel.toString()
}