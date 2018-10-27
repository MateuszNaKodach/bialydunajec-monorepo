package org.bialydunajec.registrations.domain.camper.campparticipant

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipantSnapshot
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.valueobject.ParticipationStatus
import org.bialydunajec.registrations.domain.camper.valueobject.StayDuration
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.shirt.valueobject.OrderedShirt
import javax.persistence.*
import javax.validation.constraints.NotNull

// or camp_participation!?
/*
Przemyslec czy to nie powinny byc osobny aggreagate, w sumie CampParticipant dba o aggregaty (nie moze byc zapisany np. do tej samej chatki), ale z drugiej strony strasznie kosztowne query zliczania ilosci zapisanych do chatki np.
 Osobno, najwyzej aggreguje to na statystycznych rzeczach, tam mam CampParticipant, tutaj tylko konkretny udział!!!
 Zmienić na CampParticipant i podmienić z aggregatem CampParticipant!
 Zbieranie danych o konkretnych camperach bedzie tylko read modelem, ile razy np. był na obozie.
 */
//TODO: Zastanowić się czy id CampParticipant nie powinno być różne, np. Camper Id!
//TODO: Add accepted agreements (modifable for Camp Registrations)
//TODO: Moznaby wydzielic accommodation, i to by było w jednym agregacie, który pilnuje też ilości i peselów.
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
        private var confirmedApplication: CamperApplication? = null, //Widac kto stworzył czy admin czy, alb dodać jakis applicationType, czy administracyjny

        @NotNull
        @Embedded
        private var currentCamperData: CamperApplication, //TODO: Nie można zmienić dopóki nie będzie zatwierdzony!

        @NotNull
        @Embedded
        private var stayDuration: StayDuration,

        @NotNull
        @Enumerated(EnumType.STRING)
        var participationStatus: ParticipationStatus = ParticipationStatus.WAITING_FOR_CONFIRM
) : AuditableAggregateRoot<CampParticipantId, CampParticipantEvent>(campParticipantIdGenerator.generateFrom(currentCamperData.personalData.pesel)), Versioned {
    @Version
    private var version: Long? = null

    init {
        registerEvent(CampParticipantEvent.Created(getAggregateId(), getSnapshot()))
    }

    fun changeAccommodation(cottage: Cottage) {
        if (cottage.getCampEditionId() == campRegistrationsEditionId) {
            this.currentCamperData = currentCamperData.copy(cottageId = cottage.getAggregateId())
        }
    }

    fun confirmByCamperWith(confirmedApplication: CamperApplication) {
        this.confirmedApplication = confirmedApplication
        this.participationStatus = ParticipationStatus.CONFIRMED_BY_CAMPER
    }

    fun confirmByAuthorized() {
        this.participationStatus = ParticipationStatus.CONFIRMED_BY_CAMPER
    }

    fun isConfirmed() = participationStatus == ParticipationStatus.CONFIRMED_BY_CAMPER || participationStatus == ParticipationStatus.CONFIRMED_BY_AUTHORIZED
    fun getCampRegistrationsEditionId() = campRegistrationsEditionId
    fun getCottageId() = this.currentCamperData.cottageId
    fun getPersonalData() = currentCamperData.personalData
    fun getHomeAddress() = currentCamperData.homeAddress
    fun getEmailAddress() = currentCamperData.emailAddress
    fun getPhoneNumber() = currentCamperData.phoneNumber
    fun getCamperEducation() = currentCamperData.camperEducation
    fun getStayDuration() = stayDuration
    fun getRegistrationApplication() = this.confirmedApplication
    override fun getVersion() = version
    fun getSnapshot() =
            CampParticipantSnapshot(
                    campParticipantId = getAggregateId(),
                    campRegistrationsEditionId = campRegistrationsEditionId,
                    confirmedApplication = confirmedApplication,
                    currentCamperData = currentCamperData,
                    stayDuration = stayDuration,
                    participationStatus = participationStatus
            )

}