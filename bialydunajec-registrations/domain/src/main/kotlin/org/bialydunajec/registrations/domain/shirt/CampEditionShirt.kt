package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionEvent
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
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
        private val shirtSizesFileUrl: Url? = null
) : AuditableAggregateRoot<CampEditionShirtId, CampEditionShirtEvent>(CampEditionShirtId(campRegistrationsEditionId)) {

    @OneToMany(cascade = [CascadeType.ALL])
    private var colorOptions: MutableList<ShirtColorOption> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL])
    private var sizeOptions: MutableList<ShirtSizeOption> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL])
    private var orders: MutableList<ShirtOrder> = mutableListOf()

    fun addColorOption(shirtColor: Color) {
        colorOptions.add(ShirtColorOption(shirtColor))
    }

    fun addSizeOption(shirtSize: ShirtSize) {
        sizeOptions.add(ShirtSizeOption(shirtSize))
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
                            colorOptions.find { it.getColor().name == shirtColor.name || it.getColor().hexValue == shirtColor.hexValue } == null
                    )
                    .addViolatedRuleIf(
                            SHIRT_ORDER_CAN_ONLY_BE_PLACED_FOR_AVAILABLE_SIZE,
                            sizeOptions.find { it.getSize().name == shirtSize.name || (it.getSize().length == shirtSize.length && it.getSize().width == shirtSize.width) } == null
                    ).toValidationResult()

    fun placeOrder(
            campParticipant: CampParticipant,
            shirtColor: Color,
            shirtSize: ShirtSize,
            shirtType: ShirtType
    ) {
        canPlaceOrder(campParticipant, shirtColor, shirtSize)
                .ifInvalidThrowException();

        orders.add(
                ShirtOrder(
                        campParticipant.getAggregateId(),
                        colorOptions.find { it.getColor().name == shirtColor.name || it.getColor().hexValue == shirtColor.hexValue }!!,
                        sizeOptions.find { it.getSize().name == shirtSize.name || (it.getSize().length == shirtSize.length && it.getSize().width == shirtSize.width) }!!,
                        shirtType
                )
        )
    }

    fun getCampRegistrationsEditionId() = campRegistrationsEditionId
    fun getShirtSizesFileUrl() = shirtSizesFileUrl
    fun getSizeOptions() = sizeOptions.map { it.getSnapshot() }
    fun getColorOptions() = colorOptions.map { it.getSnapshot() }
    fun getSnapshot() =
            CampEditionShirtSnapshot(
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