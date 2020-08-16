package org.bialydunajec.academicministry.domain

import org.bialydunajec.academicministry.domain.entity.AcademicPriest
import org.bialydunajec.academicministry.domain.entity.AcademicPriestId
import org.bialydunajec.academicministry.domain.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.academicministry.domain.valueobject.AcademicPriestSnapshot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.SocialMedia
import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.PersonalTitle
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
@Table(schema = "academic_ministry")
class AcademicMinistry(
        @NotBlank
        private var officialName: String,

        @NotEmpty
        private var shortName: String?,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "url", column = Column(name = "logoImageUrl")),
                AttributeOverride(name = "pathType", column = Column(name = "logoImageUrlPathType"))
        )
        private var logoImageUrl: Url? = null,

        @Embedded
        private var place: Place? = null,

        @Embedded
        private var socialMedia: SocialMedia? = null,

        @Embedded
        private var emailAddress: EmailAddress? = null,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "url", column = Column(name = "photoUrl")),
                AttributeOverride(name = "pathType", column = Column(name = "photoUrlPathType"))
        )
        private var photoUrl: Url? = null,

        @Embedded
        private var description: ExtendedDescription? = null
) : AggregateRoot<AcademicMinistryId, AcademicMinistryEvent>(AcademicMinistryId()), Versioned {

    @Version
    private var version: Long? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private var priests: MutableSet<AcademicPriest> = mutableSetOf()

    init {
        registerEvent(AcademicMinistryEvent.AcademicMinistryCreated(getSnapshot()))
    }

    fun updateWith(snapshot: AcademicMinistrySnapshot) {
        val isUpdate = snapshot != getSnapshot()
        if (isUpdate) {
            this.officialName = snapshot.officialName
            this.shortName = snapshot.shortName
            this.logoImageUrl = snapshot.logoImageUrl
            this.place = snapshot.place
            this.socialMedia = snapshot.socialMedia
            this.emailAddress = snapshot.emailAddress
            this.photoUrl = snapshot.photoUrl
            this.description = snapshot.description

            registerEvent(AcademicMinistryEvent.AcademicMinistryUpdated(getSnapshot()))
        }
    }

    fun addNewPriest(
            firstName: FirstName,
            lastName: LastName,
            personalTitle: PersonalTitle?,
            emailAddress: EmailAddress?,
            phoneNumber: PhoneNumber?,
            description: ExtendedDescription?,
            photoUrl: Url?
    ) {
        priests.add(
                AcademicPriest(
                        firstName,
                        lastName,
                        personalTitle,
                        emailAddress,
                        phoneNumber,
                        description,
                        photoUrl
                )
        )
    }

    fun updatePriest(priestId: AcademicPriestId, priest: AcademicPriestSnapshot) {
        priests.find { it.getAcademicPriestId() == priestId }?.updateWith(priest)
    }

    fun removePriest(priestId: AcademicPriestId) {
        priests.removeIf { it.getAcademicPriestId() == priestId }
    }

    fun getOfficialName() = officialName
    fun getShortName() = shortName
    fun getLogoImageUrl() = logoImageUrl
    fun getPlace() = place
    fun getSocialMedia() = socialMedia
    fun getEmailAddress() = emailAddress
    fun getPhotoUrl() = photoUrl
    fun getDescription() = description
    fun getPriestsSnapshots() = priests.map { it.getSnapshot() }
    override fun getVersion() = version

    fun getSnapshot() = AcademicMinistrySnapshot(
            academicMinistryId = getAggregateId(),
            officialName = officialName,
            shortName = shortName,
            logoImageUrl = logoImageUrl,
            place = place,
            socialMedia = socialMedia,
            emailAddress = emailAddress,
            photoUrl = photoUrl,
            description = description
    )

}