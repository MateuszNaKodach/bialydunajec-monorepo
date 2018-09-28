package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import org.bialydunajec.registrations.domain.camper.event.CamperEvent
import org.bialydunajec.registrations.domain.camper.valueobject.CamperEducation
import org.bialydunajec.registrations.domain.camper.valueobject.CamperPersonalData
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsRepository
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import javax.persistence.*
import javax.validation.constraints.NotNull

//TODO: Add accepted agreements (modifable for Camp Registrations)
@Entity
//@Table(schema = "camp_registrations")
class Camper internal constructor(
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
) : AuditableAggregateRoot<CamperId, CamperEvent>(CamperId(personalData.pesel)) {
    @Embedded
    @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
    private var cottageId: CottageId? = null

    @NotNull
    @Embedded
    @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsId")))
    private var campRegistrationsId: CampRegistrationsId? = null

    fun accommodateInCottage(cottage: Cottage) {
        this.cottageId = cottage.getAggregateId()
        this.campRegistrationsId = cottage.getCampRegistrationsId()
    }

    fun getCottageId() = cottageId
    fun getPersonalData() = personalData
    fun getHomeAddress() = homeAddress
    fun getEmailAddress() = emailAddress
    fun getPhoneNumber() = phoneNumber
    fun getCamperEducation() = camperEducation
    fun getStayDuration() = stayDuration
}