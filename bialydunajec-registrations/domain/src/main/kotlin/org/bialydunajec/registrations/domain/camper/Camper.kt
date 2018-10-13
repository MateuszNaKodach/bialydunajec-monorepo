package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.event.CamperEvent
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.*
import javax.validation.constraints.NotNull

//TODO: Add accepted agreements (modifable for Camp Registrations)
@Entity
@Table(schema = "camp_registrations")
class Camper internal constructor(
        @NotNull
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        private var cottageId: CottageId,

        @NotNull
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsEditionId")))
        private val campRegistrationsEditionId: CampRegistrationsEditionId,

        //TODO: Zmienic na listę encji! Albo zrobić osobny agregat CamperApplication!
        @NotNull
        @Embedded
        private val camperApplication: CamperApplication,

        @NotNull
        @Embedded
        private var stayDuration: StayDuration
) : AuditableAggregateRoot<CamperId, CamperEvent>(CamperId(camperApplication.personalData.pesel)) {

    fun accommodateInCottage(cottage: Cottage) {
        if (cottage.getCampEditionId() == campRegistrationsEditionId) {
            this.cottageId = cottage.getAggregateId()
        }
    }

    fun getCottageId() = cottageId
    fun getPersonalData() = camperApplication.personalData
    fun getHomeAddress() = camperApplication.homeAddress
    fun getEmailAddress() = camperApplication.emailAddress
    fun getPhoneNumber() = camperApplication.phoneNumber
    fun getCamperEducation() = camperApplication.camperEducation
    fun getStayDuration() = stayDuration
}