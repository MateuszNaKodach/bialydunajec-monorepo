package org.bialydunajec.registrations.domain.camper.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

@Embeddable
data class CamperApplication(
        @NotNull
        @Embedded
        val personalData: CamperPersonalData,

        @NotNull
        @Embedded
        val homeAddress: Address,

        @NotNull
        @Embedded
        val emailAddress: EmailAddress,

        @NotNull
        @Embedded
        val phoneNumber: PhoneNumber,

        @NotNull
        @Embedded
        val camperEducation: CamperEducation,

        @NotNull
        @Enumerated(EnumType.STRING)
        val status: CamperApplicationStatus = CamperApplicationStatus.WAITING_FOR_CONFIRM
)