package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.bialydunajec.registrations.domain.payment.entity.CampDownPaymentCommitment
import org.bialydunajec.registrations.domain.payment.entity.CampParticipationCommitment
import org.springframework.stereotype.Component

class CampParticipantCottageAccountFactory(
        val campRegistrationsEditionRepository: CampRegistrationsEditionRepository
) {

    //TODO: Sprawdzanie czy zarezerwował autobus, jak tak to tworzenie płatności za autobus!!! Tworzyc na nasluchiwaniu na event
    fun createFor(campParticipant: CampParticipant): CampParticipantCottageAccount {
        val campRegistrationsEdition = campRegistrationsEditionRepository.findById(campParticipant.getCampRegistrationsEditionId())
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        return CampParticipantCottageAccount(
                campParticipant.getAggregateId(),
                campParticipant.getCottageId(),
                campRegistrationsEdition.getDownPaymentAmount()?.let { CampDownPaymentCommitment(it) },
                CampParticipationCommitment(
                        campRegistrationsEdition.getTotalPrice()
                                .subtract(campRegistrationsEdition.getDownPaymentAmount() ?: Money.zero())
                )
        )

    }
}
