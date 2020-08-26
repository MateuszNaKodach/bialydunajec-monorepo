package org.bialydunajec.registrations.dto

import org.bialydunajec.ddd.dto.sharedkernel.ExtendedDescriptionDto


class CottageBossDto(
        val firstName: String?,

        val lastName: String?,

        val phoneNumber: String?,

        val emailAddress: String?,

        val university: String?,

        val fieldOfStudy: String?,

        val photoUrl: String?,

        val personalDescription: ExtendedDescriptionDto?
)
