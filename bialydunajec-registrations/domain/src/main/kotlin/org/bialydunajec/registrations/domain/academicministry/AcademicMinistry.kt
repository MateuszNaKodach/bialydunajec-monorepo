package org.bialydunajec.registrations.domain.academicministry

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

/**
 * Academic ministry in Camp Registrations Bounded Context
 */
@Entity
@Table(schema = "camp_registrations")
class AcademicMinistry(
        academicMinistryId: AcademicMinistryId,
        @NotBlank
        private var officialName: String,
        @NotEmpty
        private var shortName: String?,
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "logoImageUrl")))
        private var logoImageUrl: Url? = null
) : AggregateRoot<AcademicMinistryId, AcademicMinistryEvent>(academicMinistryId) {

    fun getOfficialName() = officialName
    fun getShortName() = shortName
    fun getLogoImageUrl() = logoImageUrl

}