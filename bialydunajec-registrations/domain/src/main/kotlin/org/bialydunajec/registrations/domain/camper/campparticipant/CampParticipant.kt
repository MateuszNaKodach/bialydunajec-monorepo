package org.bialydunajec.registrations.domain.camper.campparticipant

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipantSnapshot
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.valueobject.ParticipationStatus
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
//CampParticipantId - to id w trakcie 1 edycji!
//TODO: Zastanowić się czy id CampParticipant nie powinno być różne, np. Camper Id! Jedno do okreslonego zgloszenia, drugie do sledzenia.
// Bo jak na razie generowane z peselu ogarnie 1 edycje!!!
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
        camperTrackingCodeGenerator: CamperTrackingCodeGenerator,

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
) : AuditableAggregateRoot<CampParticipantId, CampParticipantEvent>(CampParticipantId()), Versioned {
    @Version
    private var version: Long? = null

    @Embedded
    private var camperTrackingCode: CamperTrackingCode = camperTrackingCodeGenerator.generateFrom(currentCamperData.personalData.pesel)

    init {
        registerEvent(CampParticipantEvent.Registered(getAggregateId(), getSnapshot()))
    }

    //TODO: Mail o zmianie chaty i naliczeniu płatności!
    fun changeAccommodation(cottage: Cottage) {
        if (cottage.getCampEditionId() == campRegistrationsEditionId) {
            this.currentCamperData = currentCamperData.copy(cottageId = cottage.getAggregateId())
        }
    }

    fun confirmByCamperWith(confirmedApplication: CamperApplication) {
        this.confirmedApplication = confirmedApplication
        this.participationStatus = ParticipationStatus.CONFIRMED_BY_CAMPER
        registerEvent(CampParticipantEvent.Confirmed(getAggregateId(), getSnapshot()))
    }

    fun confirmByAuthorized() {
        this.participationStatus = ParticipationStatus.CONFIRMED_BY_AUTHORIZED
        registerEvent(CampParticipantEvent.Confirmed(getAggregateId(), getSnapshot()))
    }

    fun unregisterByAuthorized(){
        this.participationStatus = ParticipationStatus.UNREGISTERED_BY_AUTHORIZED
        registerEvent(CampParticipantEvent.UnregisteredByAuthorized(getAggregateId(), getSnapshot()))
    }

    fun correctRegistrationData(){
        // FIXME: If is verified
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