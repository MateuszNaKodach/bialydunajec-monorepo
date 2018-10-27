package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionEvent
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule.*
import org.bialydunajec.registrations.domain.shirt.entity.*
import org.bialydunajec.registrations.domain.shirt.valueobject.CampEditionShirtSnapshot
import org.bialydunajec.registrations.domain.shirt.valueobject.Color
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSize
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtType
import javax.persistence.*

@Entity
@Table(schema = "camp_registrations")
class CampEditionShirt internal constructor(
        private val campRegistrationsEditionId: CampRegistrationsEditionId,
        private var shirtSizesFileUrl: Url? = null,
        private var ordersAllowed: Boolean = false
) : AuditableAggregateRoot<CampEditionShirtId, CampEditionShirtEvent>(CampEditionShirtId(campRegistrationsEditionId)) {

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private var colorOptions: MutableList<ShirtColorOption> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private var sizeOptions: MutableList<ShirtSizeOption> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private var orders: MutableList<ShirtOrder> = mutableListOf()

    fun update(
            shirtSizesFileUrl: Url?
    ) {
        if (shirtSizesFileUrl != this.shirtSizesFileUrl) {
            this.shirtSizesFileUrl = shirtSizesFileUrl
        }
    }

    fun canAddColorOption(shirtColor: Color) =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            SHIRT_COLOR_OPTION_CAN_NOT_BE_DUPLICATED, isShirtColorInOptions(shirtColor)
                    ).toValidationResult()

    fun addColorOption(shirtColor: Color, available: Boolean = true) {
        canAddColorOption(shirtColor)
                .ifInvalidThrowException()

        colorOptions.add(ShirtColorOption(shirtColor, available))
    }

    fun updateColorOption(
            shirtColorOptionId: ShirtColorOptionId,
            color: Color,
            available: Boolean = true) {
        /*
                TODO: Jesli wyszuka to sprawdzanie czy nie znalazł właśnie tego co aktualizaujemy
        canAddColorOption(color)
                .ifInvalidThrowException()
                */
        colorOptions.find { it.entityId == shirtColorOptionId }?.update(color, available)
    }

    fun canAddSizeOption(shirtSize: ShirtSize) =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            SHIRT_SIZE_OPTION_CAN_NOT_BE_DUPLICATED, isShirtSizeInOptions(shirtSize)
                    ).toValidationResult()

    fun addSizeOption(shirtSize: ShirtSize, available: Boolean = true) {
        canAddSizeOption(shirtSize)
                .ifInvalidThrowException()

        sizeOptions.add(ShirtSizeOption(shirtSize, available))
    }

    fun updateSizeOption(
            shirtSizeOptionId: ShirtSizeOptionId,
            shirtSize: ShirtSize,
            available: Boolean = true) {
        /*
        TODO: Jesli wyszuka to sprawdzanie czy nie znalazł właśnie tego co aktualizaujemy
        canAddSizeOption(shirtSize)
                .ifInvalidThrowException()
                */
        sizeOptions.find { it.entityId == shirtSizeOptionId }?.update(shirtSize, available)
    }

    //TODO: Externalize filters to hashCode and equals!
    fun canPlaceOrder(
            campParticipant: CampParticipant,
            shirtColor: Color,
            shirtSize: ShirtSize
    ) =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            SHIRT_ORDER_CAN_ONLY_BE_PLACED_ONLY_FOR_THIS_EDITION_CAMP_PARTICIPANT,
                            campParticipant.getCampRegistrationsEditionId() != campRegistrationsEditionId
                    )
                    .addViolatedRuleIf(
                            SHIRT_ORDER_CAN_ONLY_BE_PLACED_FOR_AVAILABLE_COLOR,
                            !isShirtColorInOptions(shirtColor)
                    )
                    .addViolatedRuleIf(
                            SHIRT_ORDER_CAN_ONLY_BE_PLACED_FOR_AVAILABLE_SIZE,
                            !isShirtSizeInOptions(shirtSize)
                    ).toValidationResult()

    private fun isShirtSizeInOptions(shirtSize: ShirtSize) =
            sizeOptions.find { it.getSize() == shirtSize } != null

    private fun isShirtColorInOptions(shirtColor: Color) =
            colorOptions.find { it.getColor() == shirtColor } != null

    fun placeOrder(
            campParticipant: CampParticipant,
            shirtColor: Color,
            shirtSize: ShirtSize
    ) {
        canPlaceOrder(campParticipant, shirtColor, shirtSize)
                .ifInvalidThrowException()

        orders.add(
                ShirtOrder(
                        campParticipant.getAggregateId(),
                        colorOptions.find { it.getColor() == shirtColor }!!,
                        sizeOptions.find { it.getSize() == shirtSize }!!
                )
        )
    }

    fun placeOrder(
            campParticipant: CampParticipant,
            shirtColorOptionId: ShirtColorOptionId,
            shirtSizeOptionId: ShirtSizeOptionId
    ) {
        // TODO: Check availability!
        orders.add(
                ShirtOrder(
                        campParticipant.getAggregateId(),
                        colorOptions.find { it.entityId == shirtColorOptionId }!!,
                        sizeOptions.find { it.entityId == shirtSizeOptionId }!!
                )
        )
    }


    fun getCampRegistrationsEditionId() = campRegistrationsEditionId
    fun getShirtSizesFileUrl() = shirtSizesFileUrl
    fun getSizeOptions() = sizeOptions.map { it.getSnapshot() }
    fun getColorOptions() = colorOptions.map { it.getSnapshot() }
    fun getSnapshot() =
            CampEditionShirtSnapshot(
                    getAggregateId(),
                    campRegistrationsEditionId,
                    shirtSizesFileUrl,
                    colorOptions.map { it.getSnapshot() },
                    sizeOptions.map { it.getSnapshot() }
            )

    companion object {
        fun createFrom(event: CampRegistrationsEditionEvent.CampRegistrationsCreated) =
                CampEditionShirt(event.campRegistrationsEditionId)
    }
}