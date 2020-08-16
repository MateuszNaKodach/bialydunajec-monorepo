package org.bialydunajec.registrations.domain.camper.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.*
import javax.validation.constraints.NotNull

@Embeddable
data class CamperApplication(
        @NotNull
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        val cottageId: CottageId,

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
        val camperEducation: CamperEducation
)