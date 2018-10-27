package org.bialydunajec.registrations.domain.shirt.entity

import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.shirt.valueobject.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(schema = "camp_registrations")
class ShirtOrder(
        @NotNull
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "aggregateId", column = Column(name = "campParticipantId"))
        )
        private val campParticipantId: CampParticipantId,

        @NotNull
        @OneToOne
        private var colorOption: ShirtColorOption,

        @NotNull
        @OneToOne
        private var sizeOption: ShirtSizeOption
) : IdentifiedEntity<ShirtOrderId> {

    @EmbeddedId
    override val entityId: ShirtOrderId = ShirtOrderId()

    private var status = OrderStatus.WAITING_FOR_CONFIRM;

    fun getSnapshot() =
            ShirtOrderSnapshot(campParticipantId, colorOption.getColor(), sizeOption.getSize())

    // Snapshot tego jakie były wartości np. kolorów w momencie zamawiania
    @Embedded
    @AttributeOverrides(
            AttributeOverride(name = "name", column = Column(name = "colorName"))
    )
    private val orderedColor: Color = colorOption.getColor()

    @Embedded
    @AttributeOverrides(
            AttributeOverride(name = "name", column = Column(name = "sizeName"))
    )
    private val orderedSize: ShirtSize = sizeOption.getSize()


}