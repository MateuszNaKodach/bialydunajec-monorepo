package org.bialydunajec.registrations.domain.shirt.entity

import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.shirt.valueobject.Color
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSize
import javax.persistence.*

@Entity
@Table(schema = "camp_registrations")
class ShirtOrder(
        @Embedded
        private val campParticipantId: CampParticipantId,

        @OneToOne
        private val colorOption: ShirtColorOption,

        @OneToOne
        private val shirtOption: ShirtSizeOption
) : IdentifiedEntity<ShirtOrderId> {

    @EmbeddedId
    override val entityId: ShirtOrderId = ShirtOrderId()

    @Embedded
    @AttributeOverrides(
            AttributeOverride(name = "name", column = Column(name = "colorName"))
    )
    private val orderedColor: Color = colorOption.getColor()

    @Embedded
    @AttributeOverrides(
            AttributeOverride(name = "name", column = Column(name = "sizeName"))
    )
    private val orderedSize: ShirtSize = shirtOption.getSize()
}