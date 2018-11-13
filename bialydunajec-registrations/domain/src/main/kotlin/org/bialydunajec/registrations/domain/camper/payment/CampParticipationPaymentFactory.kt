package org.bialydunajec.registrations.domain.camper.payment

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.springframework.stereotype.Component

@Component
class CampParticipationPaymentFactory(
        val campRegistrationsEditionRepository: CampRegistrationsEditionRepository
) {

    fun createFor(campParticipant: CampParticipant): CampParticipationPayment {
        val campRegistrationsEdition = campRegistrationsEditionRepository.findById(campParticipant.getCampRegistrationsEditionId())
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        return CampParticipationPayment(
                campParticipant.getAggregateId(),
                campParticipant.getCottageId(),
                campRegistrationsEdition.getPrice()
        )
    }
}