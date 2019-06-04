package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.payment.entity.*
import org.bialydunajec.registrations.domain.payment.valueobject.OperationType
import javax.persistence.*
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule.*
import org.bialydunajec.registrations.domain.payment.valueobject.AccountConfiguration


//TODO: Wysylanie informacji o platnosciach przy potwierdzeniu udziału!
@Entity
@Table(schema = "camp_registrations")
class CampParticipantCottageAccount internal constructor(
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campParticipantId")))
        private val campParticipantId: CampParticipantId,
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        private val cottageId: CottageId,

        @OneToOne(cascade = [CascadeType.ALL])
        private var campDownPaymentCommitment: CampDownPaymentCommitment?,

        @OneToOne(cascade = [CascadeType.ALL])
        private var campParticipationCommitment: CampParticipationCommitment,

        @OneToOne(cascade = [CascadeType.ALL])
        private var campBusCommitment: CampBusCommitment? = null //TODO: Skasowac jak ktos zrezygnuje z usa!
) : AuditableAggregateRoot<CampParticipantCottageAccountId, CampParticipantCottageAccountEvent>(CampParticipantCottageAccountId()),
        Versioned {

    init {
        registerEvent(
                CampParticipantCottageAccountEvent.Created(
                        getAggregateId(),
                        campParticipantId,
                        cottageId,
                        getCampDownPaymentCommitmentSnapshot(),
                        getCampParticipationCommitmentSnapshot(),
                        getCampBusCommitmentSnapshot()
                )
        )
    }

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private var operations: MutableSet<AccountOperation> = mutableSetOf()

    @Embedded
    private var configuration = AccountConfiguration()

    /**
     * Change to false when camp participant move to another cottage.
     * //TODO: Find better name, because when is not active sbd will think that any operation is not permitted.
     */
    private var isActiveCampParticipantAccount = true


    fun getParticipantId(): CampParticipantId {
        return campParticipantId
    }
    fun canDepositMoney() =
            ValidationResult.buffer()
                    .addViolatedRuleIfNot(
                            DEPOSIT_IS_NOT_ALLOWED_IF_ALL_COMMITMENTS_ARE_PAID,
                            (campDownPaymentCommitment != null && campDownPaymentCommitment!!.isNotPaid())
                                    || (campParticipationCommitment.isNotPaid())
                                    || (campBusCommitment != null && campBusCommitment!!.isNotPaid())
                    )
                    .addViolatedRuleIfNot(
                            DEPOSIT_IS_NOT_ALLOWED_IF_DISABLED_IN_CONFIGURATION,
                            configuration.depositEnabled
                    )
                    .toValidationResult()

    fun depositMoney(amount: Money, description: String? = null) {
        canDepositMoney()
                .ifInvalidThrowException()

        AccountOperation(OperationType.DEPOSIT, amount, description)
                .also {
                    operations.add(it)
                }.also {
                    registerEvent(CampParticipantCottageAccountEvent.MoneyDeposited(getAggregateId(), it.amount, it.description, it.getCreatedDate(), campParticipantId))
                }
    }


    fun canWithdrawMoney(amount: Money) =
            ValidationResult.buffer()
                    .addViolatedRuleIfNot(
                            WITHDRAW_AMOUNT_HAS_TO_BE_LESS_OR_EQUALS_TO_AVAILABLE_FUNDS,
                            getAvailableFunds().greaterOrEquals(amount)
                    )
                    .addViolatedRuleIfNot(
                            WITHDRAW_IS_NOT_ALLOWED_IF_DISABLED_IN_CONFIGURATION,
                            configuration.withdrawEnabled
                    )
                    .toValidationResult()

    fun withdrawMoney(amount: Money, description: String) {
        canWithdrawMoney(amount)
                .ifInvalidThrowException()

        AccountOperation(OperationType.WITHDRAW, amount, description)
                .also {
                    operations.add(it)
                }.also {
                    registerEvent(CampParticipantCottageAccountEvent.MoneyWithdrawn(getAggregateId(), it.amount, it.description
                            ?: "", it.getCreatedDate(), campParticipantId))
                }
    }

    /*
    //TODO: Consider change operation permitions only on account for new cottage
    fun updateAccountConfiguration(configuration: AccountConfiguration) {
        val isUpdate = configuration != this.configuration
        if (isUpdate) {
            this.configuration = configuration
        }
    }
    */

    /**
     * Suma wpłat i wypłat konta
     */
    fun getOperationsBalance() =
            operations.fold(Money.zero()) { acc, commitmentOperation ->
                when (commitmentOperation.type) {
                    OperationType.DEPOSIT -> acc.add(commitmentOperation.amount)
                    OperationType.WITHDRAW -> acc.subtract(commitmentOperation.amount)
                }
            }

    /**
     * Aktualna suma wpłat i wypłat pomniejszona o opłacone zobowiązania
     */
    fun getAvailableFunds() = getOperationsBalance()
            .subtract(campDownPaymentCommitment?.takeIf { it.isPaid() }?.getAmount() ?: Money.zero())
            .subtract(campParticipationCommitment.takeIf { it.isPaid() }?.getAmount() ?: Money.zero())
            .subtract(campBusCommitment?.takeIf { it.isPaid() }?.getAmount() ?: Money.zero())


    fun canPayForCampDownPaymentWithAccountFunds() =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            CAMP_DOWN_PAYMENT_HAS_TO_BE_ON_ACCOUNT_COMMITMENTS,
                            campDownPaymentCommitment == null
                    )
                    .addViolatedRuleIf(
                            CAMP_DOWN_PAYMENT_COMMITMENT_ALREADY_PAID,
                            campDownPaymentCommitment != null && campDownPaymentCommitment!!.isPaid()
                    )
                    .addViolatedRuleIfNot(
                            PAYMENT_AMOUNT_HAS_TO_BE_LESS_THAN_AVAILABLE_FUNDS,
                            campDownPaymentCommitment != null && enoughAvailableFundsToPay(campDownPaymentCommitment!!.getAmount())
                    )
                    .toValidationResult()

    fun payForCampDownPaymentWithAccountFunds() {
        canPayForCampDownPaymentWithAccountFunds()
                .ifInvalidThrowException()

        campDownPaymentCommitment
                ?.apply { markAsPaid() }
                ?.also {
                    registerEvent(CampParticipantCottageAccountEvent.CommitmentPaid(getAggregateId(), it.entityId, it.getPaidDate()!!, campParticipantId, it.getType()))
                }
    }

    fun canPayForCampParticipationWithAccountFunds() =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            CAMP_PARTICIPATION_DOWN_PAYMENT_HAS_TO_BE_PAID_BEFORE_CAMP_PARTICIPATION_COMMITMENT,
                            campDownPaymentCommitment != null && campDownPaymentCommitment!!.isNotPaid()
                    )
                    .addViolatedRuleIf(
                            CAMP_PARTICIPATION_COMMITMENT_ALREADY_PAID,
                            campParticipationCommitment.isPaid()
                    )
                    .addViolatedRuleIfNot(
                            PAYMENT_AMOUNT_HAS_TO_BE_LESS_THAN_AVAILABLE_FUNDS,
                            enoughAvailableFundsToPay(campParticipationCommitment.getAmount())
                    )
                    .toValidationResult()


    fun payForCampParticipationWithAccountFunds() {
        canPayForCampParticipationWithAccountFunds()
                .ifInvalidThrowException()

        campParticipationCommitment
                .apply { markAsPaid() }
                .also {
                    registerEvent(CampParticipantCottageAccountEvent.CommitmentPaid(getAggregateId(), it.entityId, it.getPaidDate()!!, campParticipantId, it.getType()))
                }
    }

    fun canPayForCampBusWithAccountFunds() =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            CAMP_BUS_HAS_TO_BE_ON_ACCOUNT_COMMITMENTS,
                            campBusCommitment == null
                    )
                    .addViolatedRuleIf(
                            CAMP_BUS_PAYMENT_ALREADY_PAID,
                            campBusCommitment != null && campBusCommitment!!.isPaid()
                    )
                    .addViolatedRuleIfNot(
                            PAYMENT_AMOUNT_HAS_TO_BE_LESS_THAN_AVAILABLE_FUNDS,
                            campBusCommitment != null && enoughAvailableFundsToPay(campBusCommitment!!.getAmount())
                    )
                    .toValidationResult()

    fun payForCampBusWithAccountFunds() {
        canPayForCampBusWithAccountFunds()
                .ifInvalidThrowException()

        campBusCommitment
                ?.apply { markAsPaid() }
                ?.also {
                    registerEvent(CampParticipantCottageAccountEvent.CommitmentPaid(getAggregateId(), it.entityId, it.getPaidDate()!!, campParticipantId, it.getType()))
                }
    }

    private fun enoughAvailableFundsToPay(money: Money) = getAvailableFunds().greaterOrEquals(money)

    fun getCampDownPaymentCommitmentSnapshot() = campDownPaymentCommitment?.getSnapshot()
    fun getCampParticipationCommitmentSnapshot() = campParticipationCommitment.getSnapshot()
    fun getCampBusCommitmentSnapshot() = campBusCommitment?.getSnapshot()

    @Version
    private var version: Long? = null

    override fun getVersion() = version
}