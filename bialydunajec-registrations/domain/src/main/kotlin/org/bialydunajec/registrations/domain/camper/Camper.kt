package org.bialydunajec.registrations.domain.camper

import com.sun.corba.se.pept.transport.ContactInfo
import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import org.bialydunajec.registrations.domain.camper.event.CamperEvent
import org.bialydunajec.registrations.domain.camper.valueobject.CamperEducation
import org.bialydunajec.registrations.domain.camper.valueobject.CamperPersonalData
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(schema = "camp-registrations")
class Camper private constructor(
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        private val cottageId: CottageId,

        @NotNull
        @Embedded
        private var personalData: CamperPersonalData,

        @NotNull
        @Embedded
        private var homeAddress: Address,

        @NotNull
        @Embedded
        private var emailAddress: EmailAddress,

        @NotNull
        @Embedded
        private var phoneNumber: PhoneNumber,

        @NotNull
        @Embedded
        private var camperEducation: CamperEducation,

        @NotNull
        @Embedded
        private var stayDuration: StayDuration

) : AuditableAggregateRoot<CamperId, CamperEvent>(CamperId(personalData.pesel))


//TODO: Dodać metode sprawdzajaca czy pesel sie nie powtarza, tworzenie id z hashowaniem peselu BCrypt - dla trzymania danych archiwalnych, ew. random!
class CamperFactory(

) {


}