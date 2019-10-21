package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOption
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOption
import org.bialydunajec.registrations.domain.shirt.valueobject.Color
import org.bialydunajec.registrations.domain.shirt.valueobject.OrderStatus
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtOrderSnapshot
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSize
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(schema = "camp_registrations")
class ShirtOrder internal constructor(
        @NotNull
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "aggregateId", column = Column(name = "campEditionShirtId"))
        )
        private val campEditionShirtId: CampEditionShirtId,

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
) : AuditableAggregateRoot<ShirtOrderId, ShirtOrderEvent>(ShirtOrderId()), Versioned {

    @Version
    private var version: Long? = null

    override fun getVersion() = version

    private var status = OrderStatus.WAITING_FOR_CONFIRM

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

    init {
        registerEvent(ShirtOrderEvent.OrderPlaced(getAggregateId(), getSnapshot()))
    }

    fun cancel() {
        this.status = OrderStatus.CANCELLED
        registerEvent(ShirtOrderEvent.OrderCancelled(getAggregateId(), getSnapshot()))
    }

    fun getSnapshot() =
            ShirtOrderSnapshot(campEditionShirtId, campParticipantId, colorOption.getSnapshot(), sizeOption.getSnapshot())

}
