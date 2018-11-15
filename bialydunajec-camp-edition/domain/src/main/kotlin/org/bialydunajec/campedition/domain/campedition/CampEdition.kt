package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.campedition.domain.campedition.valueobject.CampEditionSnapshot
import org.bialydunajec.campedition.domain.exception.CampEditionDomainRule
import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(schema = "camp_edition")
class CampEdition constructor(
        campEditionId: CampEditionId,
        @NotNull
        private var startDate: LocalDate,

        @NotNull
        private var endDate: LocalDate,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "value", column = Column(name = "totalPrice_value")),
                AttributeOverride(name = "currency", column = Column(name = "totalPrice_currency"))
        )
        private var totalPrice: Money,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "value", column = Column(name = "downPaymentAmount_value")),
                AttributeOverride(name = "currency", column = Column(name = "downPaymentAmount_currency"))
        )
        private var downPaymentAmount: Money? = null
) : AggregateRoot<CampEditionId, CampEditionEvent>(campEditionId), Versioned {

    @Version
    private var version: Long? = null

    fun canInit(startDate: LocalDate, endDate: LocalDate) = canUpdateDuration(startDate, endDate)

    init {
        canInit(startDate, endDate)
                .ifInvalidThrowException()
        canUpdateTotalPriceAndDownPaymentAmount(totalPrice, downPaymentAmount)
                .ifInvalidThrowException()

        registerEvent(
                CampEditionEvent.CampEditionCreated(
                        getAggregateId(),
                        startDate,
                        endDate,
                        totalPrice,
                        downPaymentAmount
                )
        )
    }

    fun canUpdateDuration(startDate: LocalDate, endDate: LocalDate) = ValidationResult.buffer()
            .addViolatedRuleIfNot(
                    CampEditionDomainRule.CAMP_EDITION_START_DATE_HAS_TO_BE_BEFORE_END_DATE,
                    startDate.isBefore(endDate)
            )
            .addViolatedRuleIfNot(
                    CampEditionDomainRule.CAMP_EDITION_END_DATE_HAS_TO_BE_AFTER_START_DATE,
                    endDate.isAfter(startDate)
            )
            .toValidationResult()


    fun canUpdateTotalPriceAndDownPaymentAmount(totalPrice: Money, downPaymentAmount: Money?) = ValidationResult.buffer()
            .addViolatedRuleIf(
                    CampEditionDomainRule.DOWN_PAYMENT_AMOUNT_HAS_TO_BE_LESS_THAN_TOTAL_PRICE,
                    downPaymentAmount != null && !downPaymentAmount.lessThan(totalPrice)
            )
            .toValidationResult()

    fun updateDuration(startDate: LocalDate, endDate: LocalDate) {
        canUpdateDuration(startDate, endDate)
                .ifInvalidThrowException()
        this.startDate = startDate
        this.endDate = endDate
        registerEvent(
                CampEditionEvent.CampEditionDurationUpdated(
                        getAggregateId(),
                        startDate,
                        endDate
                )
        )
    }

    fun getStartDate() = startDate
    fun getEndDate() = endDate
    fun getPrice() = totalPrice
    fun getSnapshot() = CampEditionSnapshot(getAggregateId(), startDate, endDate, totalPrice, downPaymentAmount)
    override fun getVersion() = version
}