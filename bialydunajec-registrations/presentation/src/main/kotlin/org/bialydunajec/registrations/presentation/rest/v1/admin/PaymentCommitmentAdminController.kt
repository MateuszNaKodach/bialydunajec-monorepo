package org.bialydunajec.registrations.presentation.rest.v1.admin

import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountId
import org.bialydunajec.registrations.domain.payment.valueobject.PaymentCommitmentType
import org.bialydunajec.registrations.presentation.rest.v1.admin.request.PayCommitmentBody
import org.springframework.web.bind.annotation.*

@RequestMapping("/rest-api/v1/admin/payment-commitment")
@RestController
internal class PaymentCommitmentAdminController(
        private val adminCommandGateway: CampRegistrationsAdminCommandGateway
) {

    @PostMapping("/down-payment/payment")
    fun payForDownPayment(@RequestBody request: PayCommitmentBody) =
            payForCommitment(request, PaymentCommitmentType.CAMP_DOWN_PAYMENT)

    @PostMapping("/camp-participation/payment")
    fun payForCampParticipation(@RequestBody request: PayCommitmentBody) =
            payForCommitment(request, PaymentCommitmentType.CAMP_PARTICIPATION)

    @PostMapping("/camp-bus/payment")
    fun payForCampBus(@RequestBody request: PayCommitmentBody) =
            payForCommitment(request, PaymentCommitmentType.CAMP_BUS_SEAT)


    private fun payForCommitment(request: PayCommitmentBody, type: PaymentCommitmentType) =
            when (request.useAccountFunds) {
                true -> adminCommandGateway.process(
                        CampRegistrationsCommand.PayCommitmentWithAccountFunds(
                                CampParticipantCottageAccountId(request.campParticipantCottageAccountId),
                                type
                        )
                )
                false -> adminCommandGateway.process(
                        CampRegistrationsCommand.PayCommitmentAndDepositMoney(
                                CampParticipantCottageAccountId(request.campParticipantCottageAccountId),
                                type
                        )
                )
            }


}