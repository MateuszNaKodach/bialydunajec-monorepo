package org.bialydunajec.registrations.application.external.event.processor

import org.bialydunajec.academicministry.messages.event.AcademicMinistryExternalEvent
import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.academicministry.CampRegistrationsAcademicMinistry
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Component
internal class AcademicMinistryExternalEventProcessor(
        @Qualifier("cottageAcademicMinistryRepositoryImpl")
        private val academicMinistryRepository: AcademicMinistryRepository
) {

    fun process(eventPayload: AcademicMinistryExternalEvent.AcademicMinistryCreated) {
        academicMinistryRepository.save(
                CampRegistrationsAcademicMinistry(
                        academicMinistryId = AcademicMinistryId(eventPayload.academicMinistryId),
                        officialName = eventPayload.officialName,
                        shortName = eventPayload.shortName,
                        logoImageUrl = eventPayload.logoImageUrl?.let { Url.ExternalUrl(it) }
                )
        )
    }

    fun process(eventPayload: AcademicMinistryExternalEvent.AcademicMinistryUpdated) {
        val academicMinistry = academicMinistryRepository.findById(AcademicMinistryId(eventPayload.academicMinistryId))
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.ACADEMIC_MINISTRY_NOT_FOUND)

        academicMinistry.updateWith(
                eventPayload.officialName,
                eventPayload.shortName,
                eventPayload.logoImageUrl?.let { Url.ExternalUrl(it) }
        )

        academicMinistryRepository.save(academicMinistry)
    }
}
