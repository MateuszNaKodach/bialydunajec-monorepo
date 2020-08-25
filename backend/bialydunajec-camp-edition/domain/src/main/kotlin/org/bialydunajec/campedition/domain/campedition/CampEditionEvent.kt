package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import java.time.LocalDate

sealed class CampEditionEvent(
        override val aggregateId: CampEditionId
) : DomainEvent<CampEditionId> {

    class CampEditionCreated(
            campEditionId: CampEditionId,
            val startDate: LocalDate,
            val endDate: LocalDate,
            val totalPrice: Money,
            val downPaymentAmount: Money?
    ) : CampEditionEvent(campEditionId){

        override fun toString(): String {
            return "CampEditionCreated(aggregateId=$aggregateId, startDate=$startDate, endDate=$endDate, totalPrice=$totalPrice, downPaymentAmount=$downPaymentAmount)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as CampEditionCreated

            if (startDate != other.startDate) return false
            if (endDate != other.endDate) return false
            if (totalPrice != other.totalPrice) return false
            if (downPaymentAmount != other.downPaymentAmount) return false

            return true
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + startDate.hashCode()
            result = 31 * result + endDate.hashCode()
            result = 31 * result + totalPrice.hashCode()
            result = 31 * result + (downPaymentAmount?.hashCode() ?: 0)
            return result
        }


    }

    class CampEditionDurationUpdated(
            campEditionId: CampEditionId,
            val startDate: LocalDate,
            val endDate: LocalDate
    ) : CampEditionEvent(campEditionId){
        override fun toString(): String {
            return "CampEditionDurationUpdated(aggregateId=$aggregateId, startDate=$startDate, endDate=$endDate)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as CampEditionDurationUpdated

            if (startDate != other.startDate) return false
            if (endDate != other.endDate) return false

            return true
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + startDate.hashCode()
            result = 31 * result + endDate.hashCode()
            return result
        }


    }

    override fun toString(): String {
        return "CampEditionEvent(aggregateId=$aggregateId)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CampEditionEvent

        if (aggregateId != other.aggregateId) return false

        return true
    }

    override fun hashCode(): Int {
        return aggregateId.hashCode()
    }


}
