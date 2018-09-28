package org.bialydunajec.registrations.domain.camper.entity

import org.bialydunajec.ddd.domain.base.persistence.AuditableEntity
import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import org.bialydunajec.registrations.domain.camper.valueobject.CamperEducation
import org.bialydunajec.registrations.domain.camper.valueobject.CamperPersonalData
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class CamperApplication(
        @EmbeddedId
        override val entityId: CamperApplicationId = CamperApplicationId(),

        @NotNull
        @Embedded
        private val personalData: CamperPersonalData,

        @NotNull
        @Embedded
        private val homeAddress: Address,

        @NotNull
        @Embedded
        private val emailAddress: EmailAddress,

        @NotNull
        @Embedded
        private val phoneNumber: PhoneNumber,

        @NotNull
        @Embedded
        private val camperEducation: CamperEducation
) : IdentifiedEntity<CamperApplicationId> {

    fun getPersonalData() = personalData
    fun getHomeAddress() = homeAddress
    fun getEmailAddress() = emailAddress
    fun getPhoneNumber() = phoneNumber
    fun getCamperEducation() = camperEducation
}