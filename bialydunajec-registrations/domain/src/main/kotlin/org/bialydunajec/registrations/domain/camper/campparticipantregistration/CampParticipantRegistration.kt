package org.bialydunajec.registrations.domain.camper.campparticipantregistration

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantEvent
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipantRegistrationSnapshot
import org.bialydunajec.registrations.domain.camper.valueobject.RegistrationStatus
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.valueobject.ParticipationStatus
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(schema = "camp_registrations")
class CampParticipantRegistration private constructor(
        @NotNull
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campParticipantId")))
        private val campParticipantId: CampParticipantId,

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
        private val originalCamperApplication: CamperApplication,

        @NotNull
        @Embedded
        private var camperApplication: CamperApplication = originalCamperApplication, //TODO: Nie można zmienić po zatwierdzeniu!

        @NotNull
        @Enumerated(EnumType.STRING)
        private var status: RegistrationStatus = RegistrationStatus.WAITING_FOR_CONFIRM
) : AuditableAggregateRoot<CampParticipantRegistrationId, CampParticipantRegistrationEvent>(CampParticipantRegistrationId(campRegistrationsEditionId)), Versioned {

    @Version
    private var version: Long? = null

    init {
        registerEvent(
                CampParticipantRegistrationEvent.Created(getAggregateId(), getSnapshot())
        )
    }

    fun confirmByCamperWithVerificationCode(verificationCode: String) {
        this.status = RegistrationStatus.CONFIRMED_BY_CAMPER
    }

    fun cancelByCamper() {
        this.status = RegistrationStatus.CANCELLED_BY_CAMPER
    }

    override fun getVersion() = version
    fun getCamperApplication() = camperApplication
    fun getSnapshot() =
            CampParticipantRegistrationSnapshot(
                    getAggregateId(),
                    campParticipantId,
                    campRegistrationsEditionId,
                    originalCamperApplication,
                    camperApplication,
                    status
            )

    companion object {
        fun createFrom(event: CampParticipantEvent.Created) =
                with(event.snapshot) {
                    CampParticipantRegistration(campParticipantId, campRegistrationsEditionId, currentCamperData)
                }
    }
}