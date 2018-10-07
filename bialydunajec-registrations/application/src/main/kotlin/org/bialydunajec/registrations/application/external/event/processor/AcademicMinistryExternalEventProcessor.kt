package org.bialydunajec.registrations.application.external.event.processor

import org.bialydunajec.academicministry.messages.event.AcademicMinistryExternalEvent
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
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
                AcademicMinistry(
                        academicMinistryId = AcademicMinistryId(eventPayload.academicMinistryId),
                        officialName = eventPayload.officialName,
                        shortName = eventPayload.shortName,
                        logoImageUrl = eventPayload.logoImageUrl?.let { Url(it) }
                )
        )
    }

    fun process(eventPayload: AcademicMinistryExternalEvent.AcademicMinistryUpdated) {
        val academicMinistry = academicMinistryRepository.findById(AcademicMinistryId(eventPayload.academicMinistryId))
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.ACADEMIC_MINISTRY_NOT_FOUND)

        academicMinistry.updateWith(
                eventPayload.officialName,
                eventPayload.shortName,
                eventPayload.logoImageUrl?.let { Url(it) }
        )

        academicMinistryRepository.save(academicMinistry)
    }
}