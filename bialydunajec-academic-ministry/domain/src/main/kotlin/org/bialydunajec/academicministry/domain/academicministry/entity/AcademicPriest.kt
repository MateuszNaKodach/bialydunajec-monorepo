package org.bialydunajec.academicministry.domain.academicministry.entity

import org.bialydunajec.academicministry.domain.academicministry.valueobject.AcademicPriestSnapshot
import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.PersonalTitle
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(schema = "academic_ministry")
internal class AcademicPriest(
        @NotNull
        @Embedded
        private var firstName: FirstName,

        @NotNull
        @Embedded
        private var lastName: LastName,

        @Embedded
        private var personalTitle: PersonalTitle?,

        @Embedded
        private var emailAddress: EmailAddress?,

        @Embedded
        private var phoneNumber: PhoneNumber?,

        @Embedded
        private var description: ExtendedDescription? = null,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "photoUrl")))
        private var photoUrl: Url? = null

) : IdentifiedEntity<AcademicPriestId>, Versioned {
    @EmbeddedId
    override val entityId: AcademicPriestId = AcademicPriestId()

    @Version
    private var version: Long? = null

    fun getAcademicPriestId() = entityId
    fun getFirstName() = firstName
    fun getLastName() = lastName
    fun getPersonalTitle() = personalTitle
    fun getEmailAddress() = emailAddress
    fun getPhoneNumber() = phoneNumber
    fun getDescription() = description
    fun getPhotoUrl() = photoUrl

    override fun getVersion(): Long? = version

    fun getSnapshot() = AcademicPriestSnapshot(firstName, lastName, personalTitle, emailAddress, phoneNumber, description, photoUrl)
}