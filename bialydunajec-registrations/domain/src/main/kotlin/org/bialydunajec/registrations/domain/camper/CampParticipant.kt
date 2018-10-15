package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.event.CamperEvent
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipationStatus
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.cottage.Cottage
import javax.persistence.*
import javax.validation.constraints.NotNull

// or camp_participation!?
/*
Przemyslec czy to nie powinny byc osobny aggreagate, w sumie CampParticipant dba o aggregaty (nie moze byc zapisany np. do tej samej chatki), ale z drugiej strony strasznie kosztowne query zliczania ilosci zapisanych do chatki np.
 Osobno, najwyzej aggreguje to na statystycznych rzeczach, tam mam CampParticipant, tutaj tylko konkretny udział!!!
 Zmienić na CampParticipant i podmienić z aggregatem CampParticipant!
 Zbieranie danych o konkretnych camperach bedzie tylko read modelem, ile razy np. był na obozie.
 */

//TODO: Add accepted agreements (modifable for Camp Registrations)
@Entity
@Table(
        schema = "camp_registrations",
        uniqueConstraints = [
            UniqueConstraint(columnNames = arrayOf("campRegistrationsEditionId", "pesel"), name = "one_camper_application_per_edition")
        ]
)
class CampParticipant internal constructor(
        campParticipantIdGenerator: CampParticipantIdGenerator,

        @NotNull
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsEditionId")))
        private val campRegistrationsEditionId: CampRegistrationsEditionId,

        @NotNull
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "cottageId.aggregateId", column = Column(name = "originalApplication_cottageId")),
                AttributeOverride(name = "personalData.firstName.firstName", column = Column(name = "originalApplication_firstName")),
                AttributeOverride(name = "personalData.lastName.lastName", column = Column(name = "originalApplication_lastName")),
                AttributeOverride(name = "personalData.gender", column = Column(name = "originalApplication_gender")),
                AttributeOverride(name = "personalData.pesel.pesel", column = Column(name = "originalApplication_pesel")),
                AttributeOverride(name = "personalData.birthDate.birthDate", column = Column(name = "originalApplication_birthDate")),
                AttributeOverride(name = "homeAddress.street.street", column = Column(name = "originalApplication_street")),
                AttributeOverride(name = "homeAddress.homeNumber.homeNumber", column = Column(name = "originalApplication_homeNumber")),
                AttributeOverride(name = "homeAddress.city.city", column = Column(name = "originalApplication_city")),
                AttributeOverride(name = "homeAddress.postalCode.postalCode", column = Column(name = "originalApplication_postalCode")),
                AttributeOverride(name = "emailAddress.email", column = Column(name = "originalApplication_emailAddress")),
                AttributeOverride(name = "phoneNumber.number", column = Column(name = "originalApplication_phoneNumber")),
                AttributeOverride(name = "camperEducation.university", column = Column(name = "originalApplication_university")),
                AttributeOverride(name = "camperEducation.faculty", column = Column(name = "originalApplication_faculty")),
                AttributeOverride(name = "camperEducation.fieldOfStudy", column = Column(name = "originalApplication_fieldOfStudy")),
                AttributeOverride(name = "camperEducation.highSchool", column = Column(name = "originalApplication_highSchool")),
                AttributeOverride(name = "camperEducation.isHighSchoolRecentGraduate", column = Column(name = "originalApplication_isHighSchoolRecentGraduate"))
        )
        private val originalApplication: CamperApplication, //Widac kto stworzył czy admin czy, alb dodać jakis applicationType, czy administracyjny

        @NotNull
        @Embedded
        private var stayDuration: StayDuration,

        @NotNull
        @Enumerated(EnumType.STRING)
        val participationStatus: CampParticipationStatus = CampParticipationStatus.WAITING_FOR_CONFIRM
) : AuditableAggregateRoot<CampParticipantId, CamperEvent>(campParticipantIdGenerator.generateFrom(originalApplication.personalData.pesel)) {
    @NotNull
    @Embedded
    private var currentCamperData: CamperApplication = originalApplication

    fun accommodateInCottage(cottage: Cottage) {
        if (cottage.getCampEditionId() == campRegistrationsEditionId) {
            this.currentCamperData = currentCamperData.copy(cottageId = cottage.getAggregateId())
        }
    }

    fun getCottageId() = this.currentCamperData.cottageId
    fun getPersonalData() = originalApplication.personalData
    fun getHomeAddress() = originalApplication.homeAddress
    fun getEmailAddress() = originalApplication.emailAddress
    fun getPhoneNumber() = originalApplication.phoneNumber
    fun getCamperEducation() = originalApplication.camperEducation
    fun getStayDuration() = stayDuration
}