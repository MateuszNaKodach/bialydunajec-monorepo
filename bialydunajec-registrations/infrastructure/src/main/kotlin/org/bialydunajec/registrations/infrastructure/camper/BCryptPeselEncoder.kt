package org.bialydunajec.registrations.infrastructure.camper

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.domain.camper.campparticipant.PeselEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
internal class BCryptPeselEncoder : PeselEncoder {

    val bCryptEncoder = BCryptPasswordEncoder(17)

    override fun encode(pesel: Pesel): String = bCryptEncoder.encode(pesel.toString())

    override fun match(rawPesel: Pesel, encodedPesel: Pesel) = bCryptEncoder.matches(rawPesel.toString(), encodedPesel.toString())

}