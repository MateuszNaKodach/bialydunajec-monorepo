package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.campedition.domain.exception.CampEditionDomainRule
import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(schema = "camp_edition")
class CampEdition internal constructor(
        campEditionId: CampEditionId,
        @NotNull
        private var startDate: LocalDate,

        @NotNull
        private var endDate: LocalDate
) : AggregateRoot<CampEditionId, CampEditionEvent>(campEditionId), Versioned {

    @Version
    private var version: Long? = null

    fun canInit(startDate: LocalDate, endDate: LocalDate) = canUpdateDuration(startDate, endDate)

    init {
        canInit(startDate, endDate)
                .ifInvalidThrowException()

        registerEvent(
                CampEditionEvent.CampEditionCreated(
                        getAggregateId(),
                        startDate,
                        endDate
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
    override fun getVersion() = version
}